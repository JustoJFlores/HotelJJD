package com.example.hoteljjd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Hotel;
import com.example.hoteljjd.model.HotelResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private SessionManager sessionManager;
    private FusedLocationProviderClient fusedLocationClient;
    private List<Marker> markers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(requireActivity());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotel, container, false);

        // Configurar bienvenida
        String userName = sessionManager.getUserName();
        TextView tvBienvenida = rootView.findViewById(R.id.tvBienvenida);
        tvBienvenida.setText("Bienvenido " + (userName != null ? userName : "Usuario"));

        // Configurar mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configuración inicial del mapa
        LatLng defaultLocation = new LatLng(-34.0, 151.0); // Coordenadas predeterminadas
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f));

        // Verificar y solicitar permisos
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
        } else {
            requestLocationPermission();
        }

        addHotelsToMap();
    }

    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void requestLocationPermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
            } else {
                Toast.makeText(getContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addHotelsToMap() {
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(getContext(), "Inicia sesión para ver los hoteles", Toast.LENGTH_LONG).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getHotels("Bearer " + token).enqueue(new Callback<HotelResponse>() {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hotel> hotels = response.body().getData();
                    if (hotels.isEmpty()) {
                        Toast.makeText(getContext(), "No se encontraron hoteles", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
                    for (Hotel hotel : hotels) {
                        if (hotel.getLatitud() != null && hotel.getLongitud() != null) {
                            LatLng hotelLocation = new LatLng(hotel.getLatitud(), hotel.getLongitud());
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(hotelLocation)
                                    .title(hotel.getNombre())
                                    .snippet("Calificación: " + hotel.getCalificacion()));
                            markers.add(marker);
                            marker.setTag(hotel);
                            boundsBuilder.include(hotelLocation);
                        }
                    }

                    adjustCameraToMarkers(boundsBuilder);
                    setMarkerClickListener();
                } else {
                    Toast.makeText(getContext(), "Error al cargar los datos: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                Log.e("API Error", "Error en la API", t);
                Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void adjustCameraToMarkers(LatLngBounds.Builder boundsBuilder) {
        if (!markers.isEmpty()) {
            fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    boundsBuilder.include(userLocation);
                }
                LatLngBounds bounds = boundsBuilder.build();
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            });
        }
    }

    private void setMarkerClickListener() {
        mMap.setOnMarkerClickListener(marker -> {
            Hotel hotel = (Hotel) marker.getTag();
            if (hotel != null) {
                HotelDetailBottomSheet bottomSheet = new HotelDetailBottomSheet(hotel);
                bottomSheet.show(getChildFragmentManager(), bottomSheet.getTag());
            }
            return true;
        });
    }
}
