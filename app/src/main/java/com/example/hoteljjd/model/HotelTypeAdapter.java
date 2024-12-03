package com.example.hoteljjd.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

import java.lang.reflect.Type;

public class HotelTypeAdapter implements JsonSerializer<Hotel>, JsonDeserializer<Hotel> {

    @Override
    public JsonElement serialize(Hotel hotel, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", hotel.getId());
        jsonObject.addProperty("nombre", hotel.getNombre());
        jsonObject.addProperty("descripcion", hotel.getDescripcion());
        jsonObject.addProperty("direccion", hotel.getDireccion());
        jsonObject.addProperty("calificacion", hotel.getCalificacion());
        jsonObject.addProperty("url_imagen", hotel.getUrlImagen());
        jsonObject.addProperty("latitud", hotel.getLatitud());
        jsonObject.addProperty("longitud", hotel.getLongitud());
        return jsonObject;
    }

    @Override
    public Hotel deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        Hotel hotel = new Hotel();
        hotel.setId(jsonObject.get("id").getAsInt());
        hotel.setNombre(jsonObject.get("nombre").getAsString());
        hotel.setDescripcion(jsonObject.get("descripcion").getAsString());
        hotel.setDireccion(jsonObject.get("direccion").getAsString());
        hotel.setCalificacion(jsonObject.get("calificacion").getAsDouble());
        hotel.setUrlImagen(jsonObject.get("url_imagen").getAsString());
        hotel.setLatitud(jsonObject.get("latitud").getAsDouble());
        hotel.setLongitud(jsonObject.get("longitud").getAsDouble());

        return hotel;
    }
}
