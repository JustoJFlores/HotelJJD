package com.example.hoteljjd.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteljjd.R;
import com.example.hoteljjd.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<Reservation> reservations;

    // Constructor por defecto
    public ReservationAdapter() {
        this.reservations = new ArrayList<>();
    }

    // Constructor que acepta una lista inicial
    public ReservationAdapter(List<Reservation> reservations) {
        this.reservations = reservations != null ? reservations : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reservation reservation = reservations.get(position);
        holder.startDateTextView.setText("Inicio: " + reservation.getFecha_inicio());
        holder.endDateTextView.setText("Fin: " + reservation.getFecha_fin());
        holder.totalPriceTextView.setText("Precio total: $" + reservation.getPrecio_total());
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    public void updateReservations(List<Reservation> reservations) {
        this.reservations = reservations != null ? reservations : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView startDateTextView, endDateTextView, totalPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            startDateTextView = itemView.findViewById(R.id.tv_start_date);
            endDateTextView = itemView.findViewById(R.id.tv_end_date);
            totalPriceTextView = itemView.findViewById(R.id.tv_total_price);
        }
    }
}
