package com.eriochrome.bartime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Aviso;

import java.util.ArrayList;

public class AvisosAdapter extends BaseAdapter {

    private ArrayList<Aviso> avisos;

    public interface ClickListener {
        void onRemoveItem(String idItem);
    }

    private ClickListener listener;

    public AvisosAdapter(ClickListener listener) {
        avisos = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return avisos.size();
    }

    @Override
    public Object getItem(int position) {
        return avisos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_aviso, viewGroup, false);
        }

        TextView textoAviso = view.findViewById(R.id.texto_aviso);
        textoAviso.setText(avisos.get(position).getTextoAviso());

        ImageButton cerrar = view.findViewById(R.id.cerrar);
        cerrar.setOnClickListener(v -> {
            listener.onRemoveItem(avisos.get(position).getId());
            avisos.remove(position);
            notifyDataSetChanged();
        });

        return view;
    }

    public void clear() {
        avisos.clear();
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<Aviso> avisos) {
        this.avisos.clear();
        this.avisos.addAll(avisos);
        notifyDataSetChanged();
    }
}
