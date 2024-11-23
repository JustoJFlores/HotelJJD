package com.example.hoteljjd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;


public class HotelFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_help, container, false);
        View rootView = inflater.inflate(R.layout.fragment_hotel, container, false);

        //Inicializa el mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Verificar so los permisos de ubucacion estan concedidos
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            //Solicitar permiso si no esta concedido
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        //Habilitar la capa de ubicacion de el mapa
        mMap.setMyLocationEnabled(true);

        //obtener la hubicacion actual
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            //obtener la latitud y longitud de la ubicacion actual
                            LatLng currentLocation = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            //añadir un marcador en la ubicacion actual
                            mMap.addMarker(new MarkerOptions()
                                    .position(currentLocation).title("Tu ubicación"));

                            //mover la camara a la ubicacion actual
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(currentLocation, 15));
                        }
                    }
                });
        //addPointsToMap();
    }

    //Aqui va algo mas...

    //Manejar el resultado de la solicitud de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResult) {
        if (requestCode == 1) {
            if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                //Si los permisos son concedidos, inicializar el mapa nuevamente
                onMapReady(mMap);
            }
        }
    }
}