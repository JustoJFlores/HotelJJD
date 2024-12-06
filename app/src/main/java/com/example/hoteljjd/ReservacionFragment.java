package com.example.hoteljjd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteljjd.adapter.ReservationAdapter;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Reservation;
import com.example.hoteljjd.model.ReservationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservacionFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReservationAdapter reservationAdapter;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservacion, container, false);

        sessionManager = new SessionManager(requireContext());
        recyclerView = view.findViewById(R.id.recyclerViewReservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        reservationAdapter = new ReservationAdapter(getContext());
        recyclerView.setAdapter(reservationAdapter);

        fetchReservations();

        return view;
    }

    private void fetchReservations() {
        String token = sessionManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(requireContext(), "Token no disponible", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getReservations("Bearer " + token).enqueue(new Callback<ReservationResponse>() {
            @Override
            public void onResponse(Call<ReservationResponse> call, Response<ReservationResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Reservation> reservations = response.body().getData();
                    reservationAdapter.updateReservations(reservations);
                } else {
                    Log.e("ReservacionFragment", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ReservationResponse> call, Throwable t) {
                Log.e("ReservacionFragment", "Error al conectar con la API", t);
            }
        });
    }
}
