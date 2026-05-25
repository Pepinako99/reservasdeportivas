package com.example.reservasdeportivas;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import dao.InstalacionDAO;
import models.Instalacion;

public class MainActivity extends AppCompatActivity {

    private ListView lvInstalaciones;
    private TextView tvBienvenida;
    private int usuarioId;
    private String usuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        usuarioNombre = getIntent().getStringExtra("usuarioNombre");

        tvBienvenida = findViewById(R.id.tvBienvenida);
        tvBienvenida.setText("¡Hola, " + usuarioNombre + "!");

        lvInstalaciones = findViewById(R.id.lvInstalaciones);

        cargarInstalaciones();
    }

    private void cargarInstalaciones() {
        InstalacionDAO dao = new InstalacionDAO();
        List<Instalacion> lista = dao.obtenerTodasInstalaciones();

        if (lista.isEmpty()) {
            Toast.makeText(this, "No hay instalaciones disponibles", Toast.LENGTH_SHORT).show();
        } else {
            //Si hay instalaciones creo el Adaptador pasandole la lista.
            InstalacionAdapter adapter = new InstalacionAdapter(this, lista, instalacion -> {
                        Intent intent = new Intent(MainActivity.this, HorariosActivity.class);
                        intent.putExtra("usuarioId", usuarioId);
                        intent.putExtra("usuarioNombre", usuarioNombre);
                        intent.putExtra("idInstalacion", instalacion.getIdInstalacion());
                        intent.putExtra("nombreInstalacion", instalacion.getNombre());
                        intent.putExtra("tipoInstalacion", instalacion.getTipo());
                        intent.putExtra("precioPorHora", instalacion.getPrecioPorHora());
                        startActivity(intent);
                    }
            );
            lvInstalaciones.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_mis_reservas) {
            Intent intent = new Intent(MainActivity.this, MisReservasActivity.class);
            intent.putExtra("usuarioId", usuarioId);
            intent.putExtra("usuarioNombre", usuarioNombre);
            startActivity(intent);
            return true;

        } else if (id == R.id.action_cerrar_sesion) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}