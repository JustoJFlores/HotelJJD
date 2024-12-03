package com.example.hoteljjd.model;

import java.util.List;

public class UsuarioResponse {
    private boolean success;
    private Usuario data; // Aquí encapsulamos el objeto Usuario

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Usuario getData() {
        return data;
    }

    public void setData(Usuario data) {
        this.data = data;
    }
}
