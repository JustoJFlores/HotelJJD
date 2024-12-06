package com.example.hoteljjd.model;

public class ReservationDetail {
    private long reservation_id;
    private long user_id;
    private long room_id;
    private String fecha_inicio;
    private String fecha_fin;
    private double precio_total;
    private String numero_habitacion;
    private String room_type;
    private double precio_por_noche;
    private boolean esta_disponible;
    private long hotel_id;
    private String hotel_name;
    private String hotel_description;
    private String hotel_address;
    private double hotel_rating;
    private String hotel_image_url;
    private double hotel_latitude;
    private double hotel_longitude;

    // Getters y setters
    public long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(double precio_total) {
        this.precio_total = precio_total;
    }

    public String getNumero_habitacion() {
        return numero_habitacion;
    }

    public void setNumero_habitacion(String numero_habitacion) {
        this.numero_habitacion = numero_habitacion;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
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

    public long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(long hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_description() {
        return hotel_description;
    }

    public void setHotel_description(String hotel_description) {
        this.hotel_description = hotel_description;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public double getHotel_rating() {
        return hotel_rating;
    }

    public void setHotel_rating(double hotel_rating) {
        this.hotel_rating = hotel_rating;
    }

    public String getHotel_image_url() {
        return hotel_image_url;
    }

    public void setHotel_image_url(String hotel_image_url) {
        this.hotel_image_url = hotel_image_url;
    }

    public double getHotel_latitude() {
        return hotel_latitude;
    }

    public void setHotel_latitude(double hotel_latitude) {
        this.hotel_latitude = hotel_latitude;
    }

    public double getHotel_longitude() {
        return hotel_longitude;
    }

    public void setHotel_longitude(double hotel_longitude) {
        this.hotel_longitude = hotel_longitude;
    }
}
