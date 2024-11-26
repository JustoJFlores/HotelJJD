package com.example.hoteljjd.model;

import java.util.List;

public class HotelResponse {
    private boolean success;
    private List<Hotel> data; // Lista de hoteles

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Hotel> getData() {
        return data;
    }

    public void setData(List<Hotel> data) {
        this.data = data;
    }
}
