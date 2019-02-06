package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Juego;

import java.util.ArrayList;

public class ListaJuegosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Juego> juegos;
    private Context context;
    private boolean esCreadoPorBar;

    public ListaJuegosAdapter(Context context, boolean esCreadoPorBar) {
        this.context = context;
        this.juegos = new ArrayList<>();
        this.esCreadoPorBar = esCreadoPorBar;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (esCreadoPorBar) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_juego_del_bar, viewGroup, false);
            return new JuegoDelBarHolder(view, context);
        }
        else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_juego, viewGroup, false);
            return new JuegoHolder(view, context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (esCreadoPorBar) {
            JuegoDelBarHolder juegoDelBarHolder = (JuegoDelBarHolder) viewHolder;
            Juego juego = juegos.get(i);
            juegoDelBarHolder.bindJuego(juego);
        }
        else {
            JuegoHolder juegoHolder = (JuegoHolder) viewHolder;
            Juego juego = juegos.get(i);
            juegoHolder.bindJuego(juego);
        }
    }

    @Override
    public int getItemCount() {
        return juegos.size();
    }

    public void setItems(ArrayList<Juego> nuevosJuegos) {
        juegos.clear();
        juegos.addAll(nuevosJuegos);
        notifyDataSetChanged();
    }

    public void clear() {
        juegos.clear();
        notifyDataSetChanged();
    }
}
