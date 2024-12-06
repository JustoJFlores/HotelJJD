package com.example.hoteljjd.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("created_at")  // Cambiado para que coincida con el JSON
    private String createdAt;

    @SerializedName("phone_number")  // Cambiado para que coincida con el JSON
    private String phoneNumber;

    @SerializedName("address")
    private String address;

    @SerializedName("profile_picture_url")  // Cambiado para que coincida con el JSON
    private String profilePictureUrl;

    @SerializedName("fullname")
    private String fullName;

    // Constructor
    public Usuario(String name, String email, String password, String createdAt, String phoneNumber, String address, String profilePictureUrl, String fullName) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.profilePictureUrl = profilePictureUrl;
        this.fullName = fullName;
    }

    public Usuario() {

    }

    // Método para validar si los datos del usuario no están vacíos
    public boolean isEmpty() {
        return (name == null || name.isEmpty()) &&
                (fullName == null || fullName.isEmpty()) &&
                (email == null || email.isEmpty()) &&
                (phoneNumber == null || phoneNumber.isEmpty()) &&
                (address == null || address.isEmpty());
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
