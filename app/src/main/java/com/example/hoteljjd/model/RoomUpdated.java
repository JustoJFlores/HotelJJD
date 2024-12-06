package com.example.hoteljjd.model;

public class RoomUpdated {
    private int room_id;
    private String numero_habitacion;
    private String tipo;
    private double precio_por_noche;
    private boolean esta_disponible;
    private int hotel_id;
    private String hotel_nombre;
    private String hotel_descripcion;
    private String hotel_direccion;
    private double hotel_calificacion;

    // Getters y setters para todos los campos
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_nombre() {
        return hotel_nombre;
    }

    public void setHotel_nombre(String hotel_nombre) {
        this.hotel_nombre = hotel_nombre;
    }

    public String getHotel_descripcion() {
        return hotel_descripcion;
    }

    public void setHotel_descripcion(String hotel_descripcion) {
        this.hotel_descripcion = hotel_descripcion;
    }

    public String getHotel_direccion() {
        return hotel_direccion;
    }

    public void setHotel_direccion(String hotel_direccion) {
        this.hotel_direccion = hotel_direccion;
    }

    public double getHotel_calificacion() {
        return hotel_calificacion;
    }

    public void setHotel_calificacion(double hotel_calificacion) {
        this.hotel_calificacion = hotel_calificacion;
    }
}
