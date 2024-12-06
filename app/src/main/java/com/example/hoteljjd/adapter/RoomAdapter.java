package com.example.hoteljjd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hoteljjd.R;
import com.example.hoteljjd.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private final List<Room> roomList = new ArrayList<>();
    private final OnRoomClickListener onRoomClickListener;

    public interface OnRoomClickListener {
        void onRoomClick(Room room);
    }

    public RoomAdapter(OnRoomClickListener listener) {
        this.onRoomClickListener = listener;
    }

    public void updateRoomList(List<Room> newRooms) {
        this.roomList.clear();

        // Ordenar las habitaciones por número de habitación
        newRooms.sort((room1, room2) -> room1.getNumero_habitacion().compareTo(room2.getNumero_habitacion()));

        this.roomList.addAll(newRooms);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomList.get(position);

        holder.numeroHabitacion.setText("Habitación: " + room.getNumero_habitacion());
        holder.tipo.setText("Tipo: " + room.getTipo());
        holder.precioPorNoche.setText("Precio: $" + room.getPrecio_por_noche());

        if (room.isEsta_disponible()) {
            holder.disponibilidad.setText("Disponible");
            holder.disponibilidad.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_green_dark));
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(android.R.color.white));
        } else {
            holder.disponibilidad.setText("No Disponible");
            holder.disponibilidad.setTextColor(holder.itemView.getContext().getColor(android.R.color.holo_red_dark));
            holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(android.R.color.darker_gray));
        }

        // Cargar la imagen con Glide
        Glide.with(holder.itemView.getContext())
                .load(room.getUrl_imagen()) // URL de la imagen
                .into(holder.roomImage);

        holder.itemView.setOnClickListener(v -> {
            if (onRoomClickListener != null) {
                onRoomClickListener.onRoomClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView numeroHabitacion, tipo, precioPorNoche, disponibilidad;
        ImageView roomImage;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroHabitacion = itemView.findViewById(R.id.tvNumeroHabitacion);
            tipo = itemView.findViewById(R.id.tvTipo);
            precioPorNoche = itemView.findViewById(R.id.tvPrecioPorNoche);
            disponibilidad = itemView.findViewById(R.id.tvDisponibilidad);
            roomImage = itemView.findViewById(R.id.ivRoomImage);
        }
    }
}
