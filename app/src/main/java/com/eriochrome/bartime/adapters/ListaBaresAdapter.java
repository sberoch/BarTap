package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.Bar;

import java.util.ArrayList;

public class ListaBaresAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //TODO: footer que no aparezca donde no corresponde

    private static final int FOOTER_VIEW = 1;

    private Context context;
    private ArrayList<Bar> bares;

    public ListaBaresAdapter(Context context) {
        this.context = context;
        this.bares = new ArrayList<>();
    }

    public void setItems(ArrayList<Bar> listaBares) {
        bares.clear();
        bares.addAll(listaBares);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;

        if (i == FOOTER_VIEW) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_no_encontras, viewGroup, false);
            return new FooterHolder(this.context, view);
        }
        else {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_bar, viewGroup, false);
            return new BarHolder(this.context, view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        try {
            if (viewHolder instanceof BarHolder) {
                BarHolder barHolder = (BarHolder) viewHolder;
                Bar bar = this.bares.get(i);
                barHolder.bindBar(bar);
            }
            else if (viewHolder instanceof FooterHolder) {
                FooterHolder footerHolder = (FooterHolder) viewHolder;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public int getItemCount() {

        if (this.bares == null) {
            return 0;
        }

        if (this.bares.size() == 0) {
            return 1;
        }

        //Uno extra para mostrar el footer
        return this.bares.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == bares.size()) {
            return FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }


    public void clear() {
        bares.clear();
        notifyDataSetChanged();
    }
}
