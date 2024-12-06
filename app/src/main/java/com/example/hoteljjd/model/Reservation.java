package com.example.hoteljjd.model;

public class Reservation {
    private Long id;
    private Long user_id;
    private Long room_id;
    private String fecha_inicio;
    private String fecha_fin;
    private Double precio_total;
    private String estado;

    // Campo para almacenar la habitación asociada
    private Room room;

    private Hotel hotel;

    public String getEstadoReservacion() {
        try {
            java.text.SimpleDateFormat inputFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date currentDate = new java.util.Date();
            java.util.Date startDate = inputFormat.parse(this.fecha_inicio);
            java.util.Date endDate = inputFormat.parse(this.fecha_fin);

            if (currentDate.before(startDate)) {
                return "Próxima";
            } else if (currentDate.after(endDate)) {
                return "Finalizada";
            } else {
                return "En Proceso";
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "Estado Desconocido";
        }
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
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

    public Double getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(Double precio_total) {
        this.precio_total = precio_total;
    }

    // Getter y setter para el campo 'room'
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getEstado() {
        return estado;  // Obtener el estado de la reservación
    }

    public void setEstado(String estado) {
        this.estado = estado;  // Establecer el estado de la reservación
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
