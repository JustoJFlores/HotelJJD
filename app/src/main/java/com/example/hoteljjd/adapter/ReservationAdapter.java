package com.example.hoteljjd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteljjd.R;
import com.example.hoteljjd.model.Reservation;
import com.example.hoteljjd.model.Room;
import com.example.hoteljjd.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {
    private List<Reservation> reservations;
    private Context context;
    private SessionManager sessionManager;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public ReservationAdapter(Context context) {
        this.context = context;
        this.reservations = new ArrayList<>();
        this.sessionManager = new SessionManager(context);
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
        Room room = reservation.getRoom();

        // Formatear fechas
        String startDate = formatDate(reservation.getFecha_inicio());
        String endDate = formatDate(reservation.getFecha_fin());

        // Información básica de la reserva
        holder.startDateTextView.setText("Inicio: " + startDate);
        holder.endDateTextView.setText("Fin: " + endDate);
        holder.totalPriceTextView.setText("Precio total: $" + reservation.getPrecio_total());

        // Determinar estado y aplicar estilo
        String estado = reservation.getEstadoReservacion();
        holder.statusTextView.setText(estado);

        // Aplicar estilos según el estado de la reservación
        switch (estado) {
            case "Próxima":
                applyProximaStyle(holder);
                break;
            case "En Proceso":
                applyEnProcesoStyle(holder);
                break;
            case "Finalizada":
                applyFinalizadaStyle(holder);
                break;
            default:
                applyDefaultStyle(holder);
        }
    }

    // Métodos para aplicar estilos específicos
    private void applyProximaStyle(ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_proxima));
        holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.color_texto_proxima));
        holder.statusTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_fondo_proxima));
    }

    private void applyEnProcesoStyle(ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_en_proceso));
        holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.color_texto_en_proceso));
        holder.statusTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_fondo_en_proceso));
    }

    private void applyFinalizadaStyle(ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_finalizada));
        holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.color_texto_finalizada));
        holder.statusTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_fondo_finalizada));
    }

    private void applyDefaultStyle(ViewHolder holder) {
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_default));
    }

    // Método para formatear fechas
    private String formatDate(String dateString) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = originalFormat.parse(dateString);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }

    // Método para actualizar reservaciones
    public void updateReservations(List<Reservation> allReservations) {
        long loggedUserId = sessionManager.getUserId();
        if (loggedUserId == -1) {
            Toast.makeText(context, "Usuario no logueado", Toast.LENGTH_SHORT).show();
            return;
        }

        this.reservations.clear();
        for (Reservation reservation : allReservations) {
            if (reservation.getUser_id() == loggedUserId) {
                reservations.add(reservation);
            }
        }

        if (reservations.isEmpty()) {
            Toast.makeText(context, "No tienes reservaciones", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return reservations.size();
    }

    // ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView startDateTextView, endDateTextView, totalPriceTextView, statusTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            startDateTextView = itemView.findViewById(R.id.tv_start_date);
            endDateTextView = itemView.findViewById(R.id.tv_end_date);
            totalPriceTextView = itemView.findViewById(R.id.tv_total_price);
            statusTextView = itemView.findViewById(R.id.tv_status);
        }
    }
}