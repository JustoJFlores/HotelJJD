package com.example.hoteljjd;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.model.Usuario;
import com.example.hoteljjd.model.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private TextView tvName, tvEmail;
    private ApiService apiService;
    private SessionManager sessionManager; // Instancia de SessionManager

    public PerfilFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar los TextViews
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);

        // Inicializar SessionManager
        sessionManager = new SessionManager(getContext());

        // Inicializar el servicio de API usando ApiClient
        apiService = ApiClient.getClient().create(ApiService.class);

        // Obtener el token de autenticación usando SessionManager
        String authToken = "Bearer " + sessionManager.getToken(); // Obtén el token

        // Verificar que el token no sea nulo
        if (authToken != null) {
            // Llamar al método para obtener el perfil si el token es válido
            getUserProfile(authToken);
        } else {
            // Manejar caso en que no se tiene token
            Toast.makeText(getContext(), "No se encuentra el token de autenticación", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    // Método para obtener el perfil del usuario
    private void getUserProfile(String authToken) {
        // Llamada a la API para obtener la información del usuario
        Call<UsuarioResponse> call = apiService.getUserProfile(authToken);

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener el usuario de la respuesta
                    UsuarioResponse usuarioResponse = response.body();
                    Usuario usuario = usuarioResponse.getData(); // Acceder al objeto Usuario

                    // Actualizar los TextViews con la información del usuario
                    tvName.setText(usuario.getName());
                    tvEmail.setText(usuario.getEmail());
                } else {
                    // Manejar error
                    Toast.makeText(getContext(), "Error al obtener el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                // Manejar error de solicitud
                Log.e("PerfilFragment", "Error en la solicitud: " + t.getMessage());
                Toast.makeText(getContext(), "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
