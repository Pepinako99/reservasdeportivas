package com.example.reservasdeportivas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import models.Reserva;

public class ReservaAdapter extends BaseAdapter {
    //Uso esto para que al pulsar Cancelar, para saber que Reserva en concreto estoy cancelando.
    public interface OnCancelarClick {
        void onClick(Reserva reserva);
    }

    private final Context contexto;
    private final List<Reserva> lista;
    private final LayoutInflater inflater;
    private final OnCancelarClick listener;

    public ReservaAdapter(Context contexto, List<Reserva> lista, OnCancelarClick listener) {
        this.contexto = contexto;
        this.lista = lista;
        this.listener = listener;
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_reserva, parent, false);
        }

        Reserva r = lista.get(position);

        TextView tvInstalacion = convertView.findViewById(R.id.tvInstalacion);
        TextView tvTipo = convertView.findViewById(R.id.tvTipo);
        TextView tvFecha = convertView.findViewById(R.id.tvFecha);
        TextView tvHora = convertView.findViewById(R.id.tvHora);
        TextView tvImporte = convertView.findViewById(R.id.tvImporte);
        Button btnCancelar = convertView.findViewById(R.id.btnCancelar);

        tvInstalacion.setText(r.getNombreInstalacion());
        //Muesta el tipo de instlación en mayusculas, si es null pone un texto vacío.
        tvTipo.setText(r.getTipoInstalacion() != null ? r.getTipoInstalacion().toUpperCase() : "");
        tvFecha.setText("📅 " + r.getFecha());
        tvHora.setText("🕒 " + r.getHoraInicio() + " - " + r.getHoraFin());
        //Muestra el coste sin decimales.
        tvImporte.setText(String.format("%.0f €", r.getImporte()));

        btnCancelar.setOnClickListener(v -> listener.onClick(r));

        return convertView;
    }
}