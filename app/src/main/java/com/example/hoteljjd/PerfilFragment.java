package com.example.hoteljjd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Usuario;
import com.example.hoteljjd.model.UsuarioResponse;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilFragment extends Fragment {

    private TextView tvName, tvFullName, tvEmail, tvPhoneNumber, tvAddress, tvCreatedAt;
    private ImageView ivProfileImage;
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar vistas
        tvName = view.findViewById(R.id.tvName);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvCreatedAt = view.findViewById(R.id.tvCreatedAt);
        ivProfileImage = view.findViewById(R.id.ivProfileImage);

        // Inicializar sessionManager y apiService
        sessionManager = new SessionManager(getContext());
        apiService = ApiClient.getClient().create(ApiService.class);

        // Obtener token de autenticación
        String authToken = "Bearer " + sessionManager.getToken();

        // Verificar si el token está disponible y obtener el perfil
        if (authToken != null) {
            fetchUserProfile(authToken);
        } else {
            Toast.makeText(getContext(), "Token no disponible", Toast.LENGTH_SHORT).show();
        }

        // Configuración del botón de editar perfil
        MaterialButton btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(v -> openEditProfileActivity());

        // Configuración del botón de cerrar sesión
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> showLogoutConfirmationDialog());

        return view;
    }

    private void fetchUserProfile(String authToken) {
        Call<UsuarioResponse> call = apiService.getUserProfile(authToken);

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario user = response.body().getData();
                    updateUI(user);
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Log.e("PerfilFragment", t.getMessage());
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Usuario user) {
        tvName.setText("Usuario: " + (user.getName() != null ? user.getName() : "No disponible"));
        tvFullName.setText("Nombre: " + (user.getFullName() != null ? user.getFullName() : "No disponible"));
        tvEmail.setText("Correo Electrónico: " + (user.getEmail() != null ? user.getEmail() : "No disponible"));
        tvPhoneNumber.setText("Teléfono: " + (user.getPhoneNumber() != null ? user.getPhoneNumber() : "No disponible"));
        tvAddress.setText("Dirección: " + (user.getAddress() != null ? user.getAddress() : "No disponible"));
        tvCreatedAt.setText("Fecha de Registro: " + formatDate(user.getCreatedAt()));

        if (user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().isEmpty()) {
            Glide.with(this)
                    .load(user.getProfilePictureUrl())
                    .placeholder(R.drawable.circle_user_solid)
                    .error(R.drawable.circle_user_solid)
                    .into(ivProfileImage);
        } else {
            ivProfileImage.setImageResource(R.drawable.circle_user_solid);
        }
    }

    private String formatDate(String originalDate) {
        if (originalDate == null || originalDate.isEmpty()) {
            return "Fecha no disponible";
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        try {
            Date date = inputFormat.parse(originalDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Fecha inválida";
        }
    }

    // Método para abrir la actividad de edición de perfil
    private void openEditProfileActivity() {
        Intent intent = new Intent(getContext(), EditarPerfilActivity.class);
        startActivity(intent); // Inicia la actividad de editar perfil
    }

    // Método para mostrar el AlertDialog de confirmación
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog)
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                .setCancelable(false) // Impide que se cierre tocando fuera del diálogo
                .setPositiveButton("Cerrar sesión", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Limpiar la sesión
                        sessionManager.logout();

                        // Redirigir a la pantalla de inicio de sesión
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish(); // Cerrar la actividad actual
                    }
                })
                .setNegativeButton("Cancelar", null) // No hace nada, simplemente cierra el diálogo
                .show();
    }
}
