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


public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etContrasena;
    private Button btnLogin;
    private TextView tvRegistro;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo     = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        btnLogin     = findViewById(R.id.btnLogin);
        tvRegistro   = findViewById(R.id.tvRegistro);

        usuarioDAO = new UsuarioDAO();

        btnLogin.setOnClickListener(v -> hacerLogin());

        tvRegistro.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class))
        );
    }

    private void hacerLogin() {
        String correo     = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();

        if(correo.isEmpty() || contrasena.isEmpty()){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = usuarioDAO.login(correo, contrasena);

        if (usuario != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("usuarioId",     usuario.getIdUsuario());
            intent.putExtra("usuarioNombre", usuario.getNombre());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}