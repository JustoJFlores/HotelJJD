package com.example.hoteljjd.api;

import com.example.hoteljjd.model.Hotel;
import com.example.hoteljjd.model.HotelResponse;
import com.example.hoteljjd.model.LoginRequest;
import com.example.hoteljjd.model.LoginResponse;
import com.example.hoteljjd.model.RegisterRequest;
import com.example.hoteljjd.model.RegisterResponse;
import com.example.hoteljjd.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
}
