package com.example.reservasdeportivas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import dao.UsuarioDAO;
import models.Usuario;


public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etContrasena, etConfirmar;
    private Button btnRegistrar;
    private TextView tvLogin;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre     = findViewById(R.id.etNombre);
        etCorreo     = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etConfirmar  = findViewById(R.id.etConfirmar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvLogin      = findViewById(R.id.tvLogin);

        usuarioDAO = new UsuarioDAO();

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registrarUsuario() {
        String nombre     = etNombre.getText().toString().trim();
        String correo     = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String confirmar  = etConfirmar.getText().toString().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (contrasena.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!contrasena.equals(confirmar)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = new Usuario(nombre, correo, contrasena);
        int id = usuarioDAO.insertarUsuario(usuario);

        if (id != -1) {
            Toast.makeText(this, "¡Registro exitoso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
            intent.putExtra("usuarioId",     id);
            intent.putExtra("usuarioNombre", nombre);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al registrar. El correo puede estar en uso.", Toast.LENGTH_LONG).show();
        }
    }
}