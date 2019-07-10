package com.eriochrome.bartime.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public class ListaBaresFavoritosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<Bar> bares;

    public ListaBaresFavoritosAdapter(Context context) {
        this.context = context;
        this.bares = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_bar, viewGroup, false);
        return new BarHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        BarHolder barHolder = (BarHolder) viewHolder;
        Bar bar = this.bares.get(i);
        barHolder.bindBar(bar);
    }

    @Override
    public int getItemCount() {
        return bares.size();
    }

    public void setItems(ArrayList<Bar> listaBares) {
        bares.clear();
        bares.addAll(listaBares);
        notifyDataSetChanged();
    }

    public void clear() {
        bares.clear();
        notifyDataSetChanged();
    }
}
