package com.example.hoteljjd.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_USER_ID = "user_id";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Guardar el token y el ID del usuario
    public void saveSession(String token, int userId) {
        editor.putString(KEY_TOKEN, token);
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    // Obtener el token
    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    // Obtener el ID del usuario
    public int getUserId() {
        return sharedPreferences.getInt(KEY_USER_ID, -1);
    }

    // Eliminar la sesión
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
