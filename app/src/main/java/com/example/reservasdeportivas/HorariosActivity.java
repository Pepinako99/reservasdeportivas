package com.example.reservasdeportivas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dao.ReservaDAO;
import models.Reserva;

public class HorariosActivity extends AppCompatActivity {

    private TextView tvInstalacion, tvFechaSeleccionada, tvResumen;
    private Button btnSeleccionarFecha, btnReservar;
    private GridLayout gridHorarios;

    private int usuarioId, idInstalacion;
    private String usuarioNombre, nombreInstalacion, tipoInstalacion;
    private double precioPorHora;
    private String fechaSeleccionada = null;

    private static final String[] HORAS = {
            "09:00","10:00","11:00","12:00","13:00","14:00",
            "15:00","16:00","17:00","18:00","19:00","20:00","21:00"
    };

    private final List<String> horasOcupadas = new ArrayList<>();
    private final List<String> horasSeleccionadas = new ArrayList<>();
    private final List<Button> botonesHora = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);

        //Obtengo el toolbar del xml, hago vivible el menú y activo el boton de navegar hacia atras.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuarioId = getIntent().getIntExtra("usuarioId", -1);
        usuarioNombre = getIntent().getStringExtra("usuarioNombre");
        idInstalacion = getIntent().getIntExtra("idInstalacion", -1);
        nombreInstalacion = getIntent().getStringExtra("nombreInstalacion");
        tipoInstalacion = getIntent().getStringExtra("tipoInstalacion");
        precioPorHora = getIntent().getDoubleExtra("precioPorHora", 15.0);

        tvInstalacion = findViewById(R.id.tvInstalacion);
        tvFechaSeleccionada = findViewById(R.id.tvFechaSeleccionada);
        tvResumen = findViewById(R.id.tvResumen);
        btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha);
        btnReservar = findViewById(R.id.btnReservar);
        gridHorarios = findViewById(R.id.gridHorarios);

        tvInstalacion.setText(nombreInstalacion + " · " + tipoInstalacion.toUpperCase());

        btnSeleccionarFecha.setOnClickListener(v -> mostrarDatePicker());
        btnReservar.setOnClickListener(v -> confirmarReserva());
    }

    private void mostrarDatePicker() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> {
                    //Guardo la fecha en formato Año-mes-día.
                    fechaSeleccionada = String.format("%04d-%02d-%02d", year, month + 1, day);
                    //Muestro la fecha en formato Día-mes-año.
                    tvFechaSeleccionada.setText("Fecha: " + String.format("%02d/%02d/%04d", day, month + 1, year));
                    cargarHorarios();
                },
                //Abro el calendario mostrando el día de hoy por defecto.
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        //Bloqueo días enteriores para que no se puedan seleccionar, ya que no puedes hacer reservas en días anteriores.
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void cargarHorarios() {
        horasSeleccionadas.clear();
        botonesHora.clear();
        gridHorarios.removeAllViews();
        actualizarResumen();

        ReservaDAO dao = new ReservaDAO();
        List<String> ocupadas = dao.obtenerHorasOcupadas(idInstalacion, fechaSeleccionada);

        horasOcupadas.clear();
        horasOcupadas.addAll(ocupadas);

        for (String hora : HORAS) {
            Button btn = new Button(this);
            btn.setText(hora);
            btn.setTextSize(12f);

            //Defino el tamaño y posición del botón dentro del GridLayout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
            params.setMargins(6, 6, 6, 6);
            btn.setLayoutParams(params);

            if (horasOcupadas.contains(hora)) {
                btn.setBackgroundColor(ContextCompat.getColor(this, R.color.hora_ocupada));
                btn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                btn.setEnabled(false);
            } else {
                btn.setBackgroundColor(ContextCompat.getColor(this, R.color.hora_libre));
                btn.setTextColor(ContextCompat.getColor(this, android.R.color.white));
                btn.setOnClickListener(v -> toggleHora(hora, btn));
            }

            botonesHora.add(btn);
            gridHorarios.addView(btn);
        }
    }

    //Con este metodo controlo los colores en los botones de las horas.
    private void toggleHora(String hora, Button btn) {
        if (horasSeleccionadas.contains(hora)) {
            horasSeleccionadas.remove(hora);
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.hora_libre));
        } else {
            horasSeleccionadas.add(hora);
            btn.setBackgroundColor(ContextCompat.getColor(this, R.color.hora_seleccionada));
        }
        actualizarResumen();
    }

    private void actualizarResumen() {
        int numHoras = horasSeleccionadas.size();
        double total = numHoras * precioPorHora;
        if (numHoras == 0) {
            tvResumen.setText("Selecciona las horas que quieres reservar");
            btnReservar.setEnabled(false);
        } else {
            tvResumen.setText(numHoras + " hora(s) seleccionada(s) → " + String.format("%.0f€", total));
            btnReservar.setEnabled(true);
        }
    }

    private void confirmarReserva() {
        if (fechaSeleccionada == null) {
            Toast.makeText(this, "Selecciona una fecha primero", Toast.LENGTH_SHORT).show();
            return;
        }
        if (horasSeleccionadas.isEmpty()) {
            Toast.makeText(this, "Selecciona al menos una hora", Toast.LENGTH_SHORT).show();
            return;
        }

        int numHoras = horasSeleccionadas.size();
        double total = numHoras * precioPorHora;

        //Meto las horas seleccionadas en una lista y las ordeno de menor a mayor.
        List<String> ordenadas = new ArrayList<>(horasSeleccionadas);
        java.util.Collections.sort(ordenadas);
        //Calculo la hora de inicio y de fin de la reserva
        String horaInicio = ordenadas.get(0);
        String ultimaHora = ordenadas.get(ordenadas.size() - 1);
        int h = Integer.parseInt(ultimaHora.split(":")[0]) + 1;
        String horaFin = String.format("%02d:00", h);

        //Muestro un dialogo con el resumen de la reserva.
        new AlertDialog.Builder(this).setTitle("Confirmar reserva")
                .setMessage("Instalación: " + nombreInstalacion + "\n" +
                            "Tipo: " + tipoInstalacion.toUpperCase() + "\n" +
                            "Fecha: " + tvFechaSeleccionada.getText().toString().replace("Fecha: ", "") + "\n" +
                            "Horas: " + horaInicio + " - " + horaFin + "\n\n" +
                            "Importe total: " + String.format("%.0f €", total) + "\n\n" +
                            "¿Deseas confirmar la reserva?"
                )
                .setPositiveButton("Confirmar", (dialog, which) -> realizarReserva(ordenadas, total))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void realizarReserva(List<String> ordenadas, double total) {
        ReservaDAO dao = new ReservaDAO();
        boolean exito = true;

        for (String hora : ordenadas) {
            //Calculo la hora final de la reserva.
            int hSig = Integer.parseInt(hora.split(":")[0]) + 1;
            String finHora = String.format("%02d:00", hSig);

            Reserva r = new Reserva();
            r.setIdUsuario(usuarioId);
            r.setIdInstalacion(idInstalacion);
            r.setFecha(fechaSeleccionada);
            r.setHoraInicio(hora);
            r.setHoraFin(finHora);
            r.setImporte(precioPorHora);

            //Inserta la reserva en la bbdd.
            if (dao.insertarReserva(r) == -1) {
                exito = false;
                break;
            }
        }

        if (exito) {
            Toast.makeText(this, "¡Reserva realizada con éxito! Total: " +
                    String.format("%.0f€", total), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Error al realizar la reserva", Toast.LENGTH_SHORT).show();
        }
    }

    //Maneja el boton de la flecha y hace que vuelva a la pantalla anterior.
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}