package com.example.hoteljjd;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.hoteljjd.model.Usuario;
import com.google.gson.Gson;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Guardar el token y el ID del usuario
    public void saveSession(String token, long userId, String userName) {
        Log.d("SessionManager", "Guardando el user_id: " + userId + " y user_name: " + userName);  // Verifica el valor antes de guardar
        editor.putString(KEY_TOKEN, token);
        editor.putLong(KEY_USER_ID, userId);  // Asegúrate de que se guarda como long
        editor.putString(KEY_USER_NAME, userName); // Guardar el nombre del usuario
        editor.apply();
    }

    // Obtener el token
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    // Obtener el ID del usuario
    public long getUserId() {
        long userId = sharedPreferences.getLong(KEY_USER_ID, -1);  // Asegúrate de que sea long
        Log.d("SessionManager", "Recuperando el user_id: " + userId);  // Verifica el valor recuperado
        return userId;
    }

    // Obtener el nombre del usuario
    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, "Guest"); // Devolver "Guest" si no se encuentra el nombre
    }

    // Eliminar la sesión
    public void clearSession() {
        Log.d("SessionManager", "Limpiando sesión...");
        editor.clear();
        editor.apply();
    }

    public void logout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token"); // Remover el token
        editor.remove("userId"); // Remover el ID de usuario
        editor.apply(); // Aplicar cambios
    }

    public void setCurrentUser(Usuario user) {
        if (user != null) {
            String userJson = new Gson().toJson(user);
            Log.d("SessionManager", "Guardando usuario JSON: " + userJson); // Log para verificar el JSON antes de guardar
            editor.putString("user", userJson);
            editor.apply();
        } else {
            Log.e("SessionManager", "Intento de guardar un usuario nulo.");
        }
    }


    public Usuario getCurrentUser() {
        String userJson = sharedPreferences.getString("user", null);
        Log.d("SessionManager", "Recuperando usuario JSON: " + userJson); // Log para verificar el JSON recuperado
        if (userJson != null) {
            try {
                return new Gson().fromJson(userJson, Usuario.class);
            } catch (Exception e) {
                Log.e("SessionManager", "Error al deserializar el usuario: " + e.getMessage());
            }
        }
        return null;
    }




}
