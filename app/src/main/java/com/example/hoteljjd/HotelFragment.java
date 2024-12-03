package com.example.hoteljjd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Hotel;
import com.example.hoteljjd.model.HotelResponse;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class HotelFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SessionManager sessionManager;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hotel, container, false);

        // Inicializa el cliente de ubicación
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Inicializa el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Habilitar la ubicación del usuario en el mapa
        if (getContext() != null && PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            mMap.setMyLocationEnabled(true);
            moveCameraToUserLocation();
        }

        // Agregar hoteles al mapa
        addHotelsToMap();
    }

    @SuppressLint("MissingPermission")
    private void moveCameraToUserLocation() {
        // Obtener la ubicación actual y mover la cámara
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), location -> {
                    if (location != null) {
                        // Crear una LatLng con la ubicación actual
                        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

                        // Mover la cámara al centro de la ubicación actual
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 15));
                    }
                });
    }

    private void addHotelsToMap() {
        String token = sessionManager.getToken(); // Recuperar token almacenado
        if (token == null || token.isEmpty()) {
            System.err.println("Token no disponible. Inicia sesión para obtener los datos.");
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getHotels("Bearer " + token).enqueue(new Callback<HotelResponse>() {

            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                Log.d("API Response", "Response Code: " + response.code());
                if (response.isSuccessful()) {
                    List<Hotel> hotels = response.body().getData();

                    for (Hotel hotel : hotels) {
                        if (hotel.getLatitud() != null && hotel.getLongitud() != null) {
                            LatLng hotelLocation = new LatLng(hotel.getLatitud(), hotel.getLongitud());

                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(hotelLocation)
                                    .title(hotel.getNombre())
                                    .snippet("Calificación: " + hotel.getCalificacion()));

                            // Asociar el marcador con su respectivo hotel
                            marker.setTag(hotel);

                            // Agregar un listener para mostrar el BottomSheet cuando se toca un marcador
                            mMap.setOnMarkerClickListener(marker1 -> {
                                Hotel hotelClicked = (Hotel) marker1.getTag();  // Obtener el hotel asociado con el marcador
                                if (hotelClicked != null) {
                                    HotelDetailBottomSheet bottomSheet = new HotelDetailBottomSheet(hotelClicked);
                                    bottomSheet.show(getChildFragmentManager(), bottomSheet.getTag());
                                }
                                return true; // Retornar true para indicar que el marcador fue manejado
                            });
                        }
                    }
                } else {
                    Log.e("API Error", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Error en la conexión de la API");
            }
        });
    }
}
