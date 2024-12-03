package com.example.hoteljjd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hoteljjd.adapter.RoomAdapter;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Room;
import com.example.hoteljjd.model.RoomResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomListActivity extends AppCompatActivity {

    private RoomAdapter roomAdapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        sessionManager = new SessionManager(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRooms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Implementar el listener para los clics en las habitaciones
        roomAdapter = new RoomAdapter(room -> {
            if (room.isEsta_disponible()) {
                // Si la habitación está disponible, redirigir a la actividad de reservación
                Intent intent = new Intent(RoomListActivity.this, ReservationActivity.class);
                intent.putExtra("room_id", room.getId());  // Asegúrate de que room.getId() sea un Long
                intent.putExtra("room_price", room.getPrecio_por_noche());
                intent.putExtra("room_number", room.getNumero_habitacion());
                startActivity(intent);
            } else {
                // Mostrar mensaje si la habitación no está disponible
                Toast.makeText(RoomListActivity.this, "La habitación no está disponible", Toast.LENGTH_SHORT).show();
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

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getRoomsByHotelId(hotelId, "Bearer " + token).enqueue(new Callback<RoomResponse>() {
            @Override
            public void onResponse(Call<RoomResponse> call, Response<RoomResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    List<Room> rooms = response.body().getData(); // Obtener habitaciones del campo "data"
                    roomAdapter.updateRoomList(rooms != null ? rooms : new ArrayList<>()); // Actualiza el adaptador

                    if (rooms == null || rooms.isEmpty()) {
                        Toast.makeText(RoomListActivity.this, "No se encontraron habitaciones para este hotel.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("RoomListActivity", "Error en la respuesta: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<RoomResponse> call, Throwable t) {
                Log.e("RoomListActivity", "Error al conectar con la API", t);
            }
        });
    }
}
