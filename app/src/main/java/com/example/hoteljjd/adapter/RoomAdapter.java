package com.example.hoteljjd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteljjd.R;
import com.example.hoteljjd.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<Room> roomList = new ArrayList<>();
    private OnRoomClickListener onRoomClickListener;

    // Interfaz para escuchar clics en las habitaciones
    public interface OnRoomClickListener {
        void onRoomClick(Room room);
    }

    public RoomAdapter(OnRoomClickListener listener) {
        this.onRoomClickListener = listener;
    }

    public void updateRoomList(List<Room> newRooms) {
        this.roomList.clear();
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
        holder.disponibilidad.setText(room.isEsta_disponible() ? "Disponible" : "No Disponible");

        holder.itemView.setOnClickListener(v -> {
            // Al hacer clic en una habitación, la actividad escuchadora la recibe
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

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            numeroHabitacion = itemView.findViewById(R.id.tvNumeroHabitacion);
            tipo = itemView.findViewById(R.id.tvTipo);
            precioPorNoche = itemView.findViewById(R.id.tvPrecioPorNoche);
            disponibilidad = itemView.findViewById(R.id.tvDisponibilidad);
        }
    }
}
