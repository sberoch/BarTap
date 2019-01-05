package com.eriochrome.bartime.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.Comentario;

import java.util.ArrayList;

public class ListaComentariosAdapter extends BaseAdapter {

    private ArrayList<Comentario> listaComentarios;

    public ListaComentariosAdapter() {
        listaComentarios = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return listaComentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaComentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.item_comentario, viewGroup, false);
        }

        Comentario comentario = listaComentarios.get(position);

        TextView nombreUsuario = view.findViewById(R.id.nombre_usuario);
        nombreUsuario.setText(comentario.getComentador());

        RatingBar ratingBar = view.findViewById(R.id.rating_bar);
        ratingBar.setRating(comentario.getEstrellas());

        TextView comentarioTexto = view.findViewById(R.id.comentario);
        comentarioTexto.setText(comentario.getComentarioText());

        return view;
    }

    public void clear() {
        listaComentarios.clear();
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<Comentario> comentarios) {
        listaComentarios.clear();
        listaComentarios.addAll(comentarios);
        notifyDataSetChanged();
    }
}
