package com.example.hoteljjd;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.hoteljjd.model.Hotel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
        ImageView hotelImageView = view.findViewById(R.id.hotel_image);
        TextView direccionTextView = view.findViewById(R.id.hotel_direccion);
        RatingBar ratingBar = view.findViewById(R.id.hotel_rating);
        MaterialButton reservarButton = view.findViewById(R.id.button_reservar);

        // Establecer los datos del hotel
        nameTextView.setText(hotel.getNombre());
        descriptionTextView.setText(hotel.getDescripcion());
        direccionTextView.setText(hotel.getDireccion());

        // Configurar la calificación en el RatingBar
        ratingBar.setRating((float) hotel.getCalificacion()); // Conversión de double a float

        // Cargar la imagen del hotel con Glide
        Glide.with(getContext())
                .load(hotel.getUrlImagen())
                .into(hotelImageView);

        // Configurar el botón Reservar
        reservarButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RoomListActivity.class);
            intent.putExtra("hotel_id",(long) hotel.getId());
            startActivity(intent);
        });

        return view;
    }
}
