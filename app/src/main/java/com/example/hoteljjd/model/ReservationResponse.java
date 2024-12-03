package com.example.hoteljjd.model;

import java.util.List;

public class ReservationResponse {
    private boolean success;
    private List<Reservation> data;

    // Getters y Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Reservation> getData() {
        return data;
    }

    public void setData(List<Reservation> data) {
        this.data = data;
    }
}
