package com.example.hoteljjd.api;

import com.example.hoteljjd.model.Hotel;
import com.example.hoteljjd.model.HotelTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Hotel.class, new HotelTypeAdapter())  // Registramos el TypeAdapter aquí
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://node-api-hotel-1641dca1ab84.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}
