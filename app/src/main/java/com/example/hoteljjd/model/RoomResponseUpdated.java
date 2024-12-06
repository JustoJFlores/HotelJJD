package com.example.hoteljjd.model;

public class RoomResponseUpdated {
    private boolean success; // Indica si la respuesta fue exitosa
    private RoomUpdated data; // Un solo objeto de habitación en lugar de una lista

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public RoomUpdated getData() {
        return data;
    }

    public void setData(RoomUpdated data) {
        this.data = data;
    }
}
