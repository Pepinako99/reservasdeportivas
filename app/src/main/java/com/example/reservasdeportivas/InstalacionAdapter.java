package com.example.reservasdeportivas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import models.Instalacion;

public class InstalacionAdapter extends BaseAdapter {

    public interface OnInstalacionClick {
        void onClick(Instalacion instalacion);
    }

    private final Context contexto;
    private final List<Instalacion> lista;
    private final LayoutInflater inflater;
    private final OnInstalacionClick listener;

    public InstalacionAdapter(Context contexto, List<Instalacion> lista, OnInstalacionClick listener) {
        this.contexto  = contexto;
        this.lista     = lista;
        this.listener  = listener;
        this.inflater  = LayoutInflater.from(contexto);
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
            convertView = inflater.inflate(R.layout.item_instalacion, parent, false);
        }

        Instalacion inst = lista.get(position);

        ImageView ivIcono       = convertView.findViewById(R.id.ivIcono);
        TextView  tvNombre      = convertView.findViewById(R.id.tvNombre);
        TextView  tvTipo        = convertView.findViewById(R.id.tvTipo);
        TextView  tvPrecio      = convertView.findViewById(R.id.tvPrecio);
        TextView  tvDescripcion = convertView.findViewById(R.id.tvDescripcion);

        tvNombre.setText(inst.getNombre());
        tvTipo.setText(inst.getTipo().toUpperCase());
        //Igual que en el otro Adapter, muestra el coste sin decimales.
        tvPrecio.setText(String.format("%.0f €/hora", inst.getPrecioPorHora()));
        tvDescripcion.setText(inst.getDescripcion());
        ivIcono.setImageResource(getIconoParaTipo(inst.getTipo()));

        convertView.setOnClickListener(v -> listener.onClick(inst));

        return convertView;
    }

    private int getIconoParaTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "padel":
                return R.drawable.padel;
            case "futsal":
                return R.drawable.futsal;
            case "futbol11":
                return R.drawable.fut11;
            case "futbol7":
                return R.drawable.fut7;
            case "baloncesto":
                return R.drawable.baloncesto;
            default:
                return R.drawable.padel;
        }
    }
}