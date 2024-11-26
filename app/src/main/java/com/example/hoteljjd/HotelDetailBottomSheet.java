package com.example.hoteljjd;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.hoteljjd.model.Hotel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.widget.ImageView;
import android.widget.TextView;

public class HotelDetailBottomSheet extends BottomSheetDialogFragment {

    private Hotel hotel;

    // Constructor que recibe un hotel
    public HotelDetailBottomSheet(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_hotel_detail, container, false);

        // Configurar los elementos de la vista
        TextView nameTextView = view.findViewById(R.id.hotel_name);
        TextView descriptionTextView = view.findViewById(R.id.hotel_description);
        TextView priceTextView = view.findViewById(R.id.hotel_price);
        ImageView hotelImageView = view.findViewById(R.id.hotel_image);

        // Establecer los datos del hotel
        nameTextView.setText(hotel.getNombre());
        descriptionTextView.setText(hotel.getDescripcion());
        priceTextView.setText("Precio: $" + hotel.getPrecioPorNoche());

        // Cargar la imagen del hotel con Glide
        Glide.with(getContext())
                .load(hotel.getUrlImagen())
                .into(hotelImageView);

        return view;
    }
}
