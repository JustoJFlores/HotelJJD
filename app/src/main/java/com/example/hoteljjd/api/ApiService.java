package com.example.hoteljjd.api;

import com.example.hoteljjd.model.LoginRequest;
import com.example.hoteljjd.model.LoginResponse;
import com.example.hoteljjd.model.RegisterRequest;
import com.example.hoteljjd.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

    public interface ApiService {

        @POST("login")
        Call<LoginResponse> login(@Body LoginRequest loginRequest);

        @POST("register")
        Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

}
