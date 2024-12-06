package com.example.hoteljjd.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private boolean success;
    private String token;

    @SerializedName("user") // Cambiar de "data" a "user"
    private Usuario usuario;

    @SerializedName("message")
    private String message;

    // Getters y setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
