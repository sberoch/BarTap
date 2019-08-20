package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListaBaresAReclamarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Bar> bares;
    private Context context;

    public ListaBaresAReclamarAdapter(Context context) {
        this.context = context;
        this.bares = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_bar_sin_estrellas, viewGroup, false);
        return new BarReclamarHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        BarReclamarHolder barReclamarHolder = (BarReclamarHolder) viewHolder;
        Bar bar = bares.get(position);
        barReclamarHolder.bind(bar);
    }

    @Override
    public int getItemCount() {
        return bares.size();
    }

    public void setItems(ArrayList<Bar> nuevosBares) {
        bares.clear();
        bares.addAll(nuevosBares);
        notifyDataSetChanged();
    }

    public void clear() {
        bares.clear();
        notifyDataSetChanged();
    }
}
