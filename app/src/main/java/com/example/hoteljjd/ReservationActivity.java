package com.example.hoteljjd;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Reservation;
import com.example.hoteljjd.model.Room;
import com.example.hoteljjd.model.RoomResponse;
import com.example.hoteljjd.model.RoomResponseUpdated;
import com.example.hoteljjd.model.RoomUpdated;
import com.example.hoteljjd.model.UsuarioResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationActivity extends AppCompatActivity {

    private EditText startDateEditText, endDateEditText;
    private TextView roomNumberTextView, hotelNameTextView; // hotelNameTextView agregado
    private Button reserveButton;
    private Long roomId;
    private Double roomPrice;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Inicializar vistas
        startDateEditText = findViewById(R.id.start_date);
        endDateEditText = findViewById(R.id.end_date);
        roomNumberTextView = findViewById(R.id.room_number);
        hotelNameTextView = findViewById(R.id.hotel_name); // Nuevo TextView para el hotel
        reserveButton = findViewById(R.id.btn_reserve);

        calendar = Calendar.getInstance();

        // Obtener roomId y otros detalles
        Integer roomIdInteger = getIntent().getIntExtra("room_id", -1);
        roomId = roomIdInteger != null ? roomIdInteger.longValue() : -1L;
        roomPrice = getIntent().getDoubleExtra("room_price", 0.0);
        String roomNumber = getIntent().getStringExtra("room_number");

        // Verificar si el roomId es válido
        if (roomId <= 0) {
            Toast.makeText(this, "ID de habitación inválido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        roomNumberTextView.setText("Habitación seleccionada: " + roomNumber);

        // Llamar a la API para obtener la información de la habitación con su hotel asociado
        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "No se encontró el token de autorización", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getRoomWithHotel(roomId, "Bearer " + token).enqueue(new Callback<RoomResponseUpdated>() {
            @Override
            public void onResponse(Call<RoomResponseUpdated> call, Response<RoomResponseUpdated> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RoomResponseUpdated roomResponse = response.body();
                    RoomUpdated room = roomResponse.getData(); // Obtenemos el objeto de habitación

                    if (room != null) {
                        // Mostrar el nombre del hotel y otros detalles
                        hotelNameTextView.setText("Hotel: " + room.getHotel_nombre());
                    } else {
                        Toast.makeText(ReservationActivity.this, "No se encontró información de la habitación", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ReservationActivity.this, "No se pudo obtener la información de la habitación", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RoomResponseUpdated> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ReservationActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });




        // Configurar los campos para abrir el calendario
        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));

        // Configurar el botón de reserva
        reserveButton.setOnClickListener(v -> createReservation());
    }

    private void showDatePickerDialog(EditText editText) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Formatear la fecha seleccionada
                    String formattedDate = String.format(Locale.getDefault(), "%d-%02d-%02d",
                            selectedYear, selectedMonth + 1, selectedDay);
                    editText.setText(formattedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void createReservation() {
        String startDate = startDateEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        if (startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionManager sessionManager = new SessionManager(this);
        String token = sessionManager.getToken();

        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "No se encontró el token de autorización", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.getUserProfile("Bearer " + token).enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    long userId = response.body().getData().getId();

                    int nights = calculateNights(startDate, endDate);
                    Double totalPrice = nights * roomPrice;

                    Reservation reservation = new Reservation();
                    reservation.setRoom_id(roomId);
                    reservation.setUser_id(userId); // No necesitas convertirlo en Long nuevamente
                    reservation.setFecha_inicio(startDate);
                    reservation.setFecha_fin(endDate);
                    reservation.setPrecio_total(totalPrice);

                    createReservationInApi(reservation, token);
                } else {
                    Toast.makeText(ReservationActivity.this, "No se pudo obtener el perfil del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ReservationActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createReservationInApi(Reservation reservation, String token) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        apiService.createReservation(reservation, "Bearer " + token).enqueue(new Callback<Reservation>() {
            @Override
            public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ReservationActivity.this, "Reservación exitosa", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(ReservationActivity.this, "Error al crear la reservación", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Reservation> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ReservationActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    private int calculateNights(String startDate, String endDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);

            long diffInMillis = end.getTime() - start.getTime();
            return (int) (diffInMillis / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

