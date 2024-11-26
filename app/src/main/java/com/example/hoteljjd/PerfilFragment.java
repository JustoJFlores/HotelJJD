package com.example.hoteljjd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private TextInputEditText profileNameEditText;
    private TextInputEditText profileEmailEditText;
    com.example.hoteljjd.utils.SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        profileNameEditText = view.findViewById(R.id.profileNameEditText);
        profileEmailEditText = view.findViewById(R.id.profileEmailEditText);

        // Suponiendo que tienes el token de autenticación
        String token = sessionManager.getToken();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Usuario> call = apiService.getUserInfo(token);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario user = response.body();
                    profileNameEditText.setText(user.getName());
                    profileEmailEditText.setText(user.getEmail());
                } else {
                    Toast.makeText(getContext(), "Error al obtener la información del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}