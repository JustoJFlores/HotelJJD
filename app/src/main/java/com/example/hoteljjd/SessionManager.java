package com.example.hoteljjd;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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
    public void saveSession(String token, long userId) {
        Log.d("SessionManager", "Guardando el user_id: " + userId);  // Verifica el valor antes de guardar
        editor.putString(KEY_TOKEN, token);
        editor.putLong(KEY_USER_ID, userId);  // Asegúrate de que se guarda como long
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

    // Eliminar la sesión
    public void clearSession() {
        Log.d("SessionManager", "Limpiando sesión...");
        editor.clear();
        editor.apply();
    }
}
