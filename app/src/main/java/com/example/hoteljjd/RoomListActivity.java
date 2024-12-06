package com.example.hoteljjd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteljjd.adapter.RoomAdapter;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Room;
import com.example.hoteljjd.model.RoomResponse;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListActivity extends AppCompatActivity {

    private RoomAdapter roomAdapter;
    private SessionManager sessionManager;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        sessionManager = new SessionManager(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRooms);
        progressBar = findViewById(R.id.progressBar);

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar); // Configura la barra de herramientas como ActionBar

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Habitaciones");
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomAdapter = new RoomAdapter(room -> {
            if (room.isEsta_disponible()) {
                Intent intent = new Intent(RoomListActivity.this, ReservationActivity.class);
                intent.putExtra("room_id", room.getId());
                intent.putExtra("room_price", room.getPrecio_por_noche());
                intent.putExtra("room_number", room.getNumero_habitacion());
                startActivity(intent);
            } else {
                Snackbar.make(recyclerView, "La habitación no está disponible", Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(roomAdapter);

        long hotelId = getIntent().getLongExtra("hotel_id", -1);

        fetchRooms(hotelId);
    }

    private void fetchRooms(long hotelId) {
        String token = sessionManager.getToken();

        if (token == null || token.isEmpty()) {
            Log.e("RoomListActivity", "Token no disponible.");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getRoomsByHotelId(hotelId, "Bearer " + token).enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Room> rooms = response.body().getData();
                    roomAdapter.updateRoomList(rooms != null ? rooms : new ArrayList<>());

                    if (rooms == null || rooms.isEmpty()) {
                        Snackbar.make(findViewById(R.id.recyclerViewRooms),
                                "No se encontraron habitaciones para este hotel.",
                                Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("RoomListActivity", "Error en la respuesta: " + response.message());
                    Snackbar.make(findViewById(R.id.recyclerViewRooms),
                            "Error al cargar las habitaciones.",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("RoomListActivity", "Error al conectar con la API", t);
                Snackbar.make(findViewById(R.id.recyclerViewRooms),
                        "Error al conectar con el servidor. Intente de nuevo más tarde.",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
