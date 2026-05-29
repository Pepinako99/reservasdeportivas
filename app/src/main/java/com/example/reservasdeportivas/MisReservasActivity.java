package com.example.reservasdeportivas;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import dao.ReservaDAO;
import models.Reserva;

public class MisReservasActivity extends AppCompatActivity {

    private ListView lvReservas;
    private TextView tvSinReservas;

    private int usuarioId;
    private String usuarioNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_reservas);

        //Obtengo el toolbar del xml, hago visible el menú y activo el boton de navegar hacia atras.
        //Además, le cambio el titulo.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Mis reservas");
        }
        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        usuarioNombre = getIntent().getStringExtra("usuarioNombre");

        lvReservas = findViewById(R.id.lvReservas);
        tvSinReservas = findViewById(R.id.tvSinReservas);

        cargarReservas();
    }

    private void cargarReservas() {
        ReservaDAO dao = new ReservaDAO();
        List<Reserva> lista = dao.obtenerReservasUsuario(usuarioId);

        //Si no hay reservas oculto la lista y dejo un mensaje.
        if (lista == null || lista.isEmpty()) {
            lvReservas.setVisibility(View.GONE);
            tvSinReservas.setVisibility(View.VISIBLE);
            //Si hay lista, muestro la lista y oculto el texto.
        } else {
            tvSinReservas.setVisibility(View.GONE);
            lvReservas.setVisibility(View.VISIBLE);

            ReservaAdapter adapter = new ReservaAdapter(this, lista,
                    reserva -> confirmarCancelacion(reserva)
            );
            lvReservas.setAdapter(adapter);
        }
    }

    private void confirmarCancelacion(Reserva reserva) {
        //Muestro un dialogo con el resumente de la reserva a cancelar.
        new AlertDialog.Builder(this)
                .setTitle("Cancelar reserva")
                .setMessage(
                        "¿Deseas cancelar la reserva de "
                                + reserva.getNombreInstalacion()
                                + "?\n\nFecha: "
                                + reserva.getFecha()
                                + "\nHora: "
                                + reserva.getHoraInicio()
                                + " - "
                                + reserva.getHoraFin()
                )
                .setPositiveButton("Cancelar reserva", (dialog, which) -> {
                    ReservaDAO dao = new ReservaDAO();
                    boolean ok = dao.cancelarReserva(reserva.getIdReserva());

                    if (ok) {
                        Toast.makeText(this, "Reserva cancelada", Toast.LENGTH_SHORT).show();
                        cargarReservas();
                    } else {
                        Toast.makeText(this, "Error al cancelar la reserva", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Mantener", null)
                .show();
    }

    //Al igual que en Horario, este es el método que hace que la fecha cambie a la pantalla anterior.
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}