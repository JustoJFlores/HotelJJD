package com.example.hoteljjd.model;

public class Room {
    private int id;
    private int hotel_id;
    private String numero_habitacion;
    private String tipo;
    private double precio_por_noche;
    private boolean esta_disponible;
    private String creado_en;
    private String url_imagen;

    // Getters y setters para todos los campos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(String numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio_por_noche() {
        return precio_por_noche;
    }

    public void setPrecio_por_noche(double precio_por_noche) {
        this.precio_por_noche = precio_por_noche;
    }

    public boolean isEsta_disponible() {
        return esta_disponible;
    }

    public void setEsta_disponible(boolean esta_disponible) {
        this.esta_disponible = esta_disponible;
    }

    public String getCreado_en() {
        return creado_en;
    }

    public void setCreado_en(String creado_en) {
        this.creado_en = creado_en;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }
}
