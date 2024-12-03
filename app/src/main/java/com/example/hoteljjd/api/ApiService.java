package com.example.hoteljjd.api;

import com.example.hoteljjd.model.HotelResponse;
import com.example.hoteljjd.model.LoginRequest;
import com.example.hoteljjd.model.LoginResponse;
import com.example.hoteljjd.model.RegisterRequest;
import com.example.hoteljjd.model.RegisterResponse;
import com.example.hoteljjd.model.ReservationResponse;
import com.example.hoteljjd.model.RoomResponse;
import com.example.hoteljjd.model.Usuario;
import com.example.hoteljjd.model.Reservation;
import com.example.hoteljjd.model.UsuarioResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

        @POST("api/auth/login")
        Call<LoginResponse> login(@Body LoginRequest loginRequest);

        @POST("api/auth/register")
        Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

        //obtener la lista de hoteles
        @GET("api/hotels")
        Call<HotelResponse> getHotels(@Header("Authorization") String token);

        @GET("api/users")
        Call<Usuario> getUserInfo(@Header("Authorization") String token);

        // Obtener habitaciones por hotel_id
        @GET("api/rooms/hotel/{hotelId}")
        Call<RoomResponse> getRoomsByHotelId(@Path("hotelId") long hotelId, @Header("Authorization") String token);

        @GET("api/users/me")
        Call<UsuarioResponse> getUserProfile(@Header("Authorization") String authHeader);

        @POST("api/reservations")
        Call<Reservation> createReservation(@Body Reservation reservation, @Header("Authorization") String authHeader);

        @GET("api/reservations")
        Call<ReservationResponse> getReservations(@Header("Authorization") String token);
}
