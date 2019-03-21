package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

import java.util.ArrayList;

public class ListaComprasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private boolean esBar;
    private ArrayList<ComprobanteDeCompra> compras;

    public ListaComprasAdapter(Context context, boolean esBar) {
        this.context = context;
        this.esBar = esBar;
        this.compras = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_comprobante_compra, viewGroup, false);
        return new ComprobanteCompraHolder(view, context, esBar);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ComprobanteCompraHolder comprobanteHolder = (ComprobanteCompraHolder) viewHolder;
        ComprobanteDeCompra comprobanteDeCompra = compras.get(i);
        comprobanteHolder.bind(comprobanteDeCompra);
    }

    @Override
    public int getItemCount() {
        return compras.size();
    }

    public void setItems(ArrayList<ComprobanteDeCompra> nuevasCompras) {
        compras.clear();
        compras.addAll(nuevasCompras);
        notifyDataSetChanged();
    }

    public void clear() {
        compras.clear();
        notifyDataSetChanged();
    }
}
