package com.example.hoteljjd;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoteljjd.api.ApiClient;
import com.example.hoteljjd.api.ApiService;
import com.example.hoteljjd.model.Usuario;
import com.example.hoteljjd.model.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText addressEditText;
    private EditText fullnameEditText;
    private EditText passwordEditText;
    private Button saveButton;
    private ApiService apiService;
    private SessionManager sessionManager;
    private long userId; // Para almacenar el ID del usuario
    private String token; // Para almacenar el token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Inicializar vistas y SessionManager
        nameEditText = findViewById(R.id.etName);
        emailEditText = findViewById(R.id.etEmail);
        phoneEditText = findViewById(R.id.etPhoneNumber);
        addressEditText = findViewById(R.id.etAddress);
        fullnameEditText = findViewById(R.id.etFullName);
        passwordEditText = findViewById(R.id.etPassword);
        saveButton = findViewById(R.id.btnSaveChanges);

        // Inicializar el servicio API
        apiService = ApiClient.getClient().create(ApiService.class);

        // Inicializar SessionManager
        sessionManager = new SessionManager(this);

        // Obtener el ID del usuario y el token desde el SessionManager
        userId = sessionManager.getUserId();
        token = sessionManager.getToken();

        // Verificar si hay un usuario en sesión
        if (userId == -1 || token == null) {
            Toast.makeText(this, "No se ha encontrado el usuario en sesión", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Llenar los campos con la información actual del usuario
        getUserInfo(userId, token);

        // Listener para el botón de guardar cambios
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileChanges(token);
            }
        });
    }

    private void getUserInfo(long userId, String token) {
        Call<UsuarioResponse> call = apiService.getUser(userId, "Bearer " + token);

        call.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuario = response.body().getData();
                    if (usuario != null) {
                        // Llenar los campos con la información del usuario
                        nameEditText.setText(usuario.getName());
                        emailEditText.setText(usuario.getEmail());
                        phoneEditText.setText(usuario.getPhoneNumber());
                        addressEditText.setText(usuario.getAddress());
                        fullnameEditText.setText(usuario.getFullName());
                    } else {
                        Toast.makeText(EditarPerfilActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Error al obtener la información del perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(EditarPerfilActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveProfileChanges(String token) {
        // Obtener los valores de los campos editables
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String fullname = fullnameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Verificar que todos los campos estén completos
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || fullname.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener la información del usuario actual para mantener los valores existentes
        Call<UsuarioResponse> getUserCall = apiService.getUser(userId, "Bearer " + token);
        getUserCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario currentUser = response.body().getData();
                    if (currentUser != null) {
                        // Usar los valores existentes si no se proporcionan nuevos
                        String currentProfilePictureUrl = currentUser.getProfilePictureUrl();
                        String currentPassword = currentUser.getPassword();
                        String currentCreatedAt = currentUser.getCreatedAt();

                        // Solo modificar la contraseña si se ingresa una nueva
                        String passwordToUpdate = password.isEmpty() ? currentPassword : password;

                        // Crear el objeto Usuario con los nuevos datos
                        Usuario updatedUser = new Usuario(
                                name,
                                email,
                                passwordToUpdate,
                                currentCreatedAt,
                                phone,
                                address,
                                currentProfilePictureUrl,
                                fullname
                        );

                        // Enviar los cambios a la API con el token en la cabecera
                        Call<UsuarioResponse> updateCall = apiService.updateUser(userId, updatedUser, "Bearer " + token);

                        updateCall.enqueue(new Callback<UsuarioResponse>() {
                            @Override
                            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    Usuario usuario = response.body().getData();
                                    if (usuario != null) {
                                        // Actualizar la información en el SessionManager
                                        sessionManager.saveSession(usuario.getEmail(), usuario.getId(), usuario.getName());
                                        Toast.makeText(EditarPerfilActivity.this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditarPerfilActivity.this, "Error al actualizar perfil", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(EditarPerfilActivity.this, "Error al actualizar perfil. Código: " + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                                Toast.makeText(EditarPerfilActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Error al obtener información actual del usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {
                Toast.makeText(EditarPerfilActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}