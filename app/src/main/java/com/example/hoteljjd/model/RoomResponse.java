package com.example.hoteljjd.model;

import java.util.List;

public class RoomResponse {
    private boolean success; // Indica si la respuesta fue exitosa
    private List<Room> data; // La lista de habitaciones está en "data"

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Room> getData() {
        return data;
    }

    public void setData(List<Room> data) {
        this.data = data;
    }
}
