package com.eriochrome.bartime.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;

import java.util.ArrayList;

public class ListaItemsTiendaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<ItemTienda> listaItems;
    private boolean esCreadoPorBar;
    private ItemTiendaBarHolder.HolderCallback listenerItemBar;
    private ItemTiendaHolder.ItemTiendaClickListener listenerItem;

    public ListaItemsTiendaAdapter(Context context, boolean esCreadoPorBar) {
        this.context = context;
        listaItems = new ArrayList<>();
        this.esCreadoPorBar = esCreadoPorBar;
    }

    public void setListenerBar(ItemTiendaBarHolder.HolderCallback listener) {
        this.listenerItemBar = listener;
    }

    public void setListener(ItemTiendaHolder.ItemTiendaClickListener listener) {
        this.listenerItem = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (esCreadoPorBar) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_item_tienda_bar, viewGroup, false);
            return new ItemTiendaBarHolder(view, context);

        } else {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_item_tienda, viewGroup, false);
            return new ItemTiendaHolder(view, context);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (esCreadoPorBar) {
            ItemTiendaBarHolder itemTiendaBarHolder = (ItemTiendaBarHolder) viewHolder;
            ItemTienda itemTienda = listaItems.get(i);
            itemTiendaBarHolder.bindItem(itemTienda, listenerItemBar, () -> {
                listaItems.remove(i);
                notifyDataSetChanged();
            });

        } else {
            ItemTiendaHolder itemTiendaHolder = (ItemTiendaHolder) viewHolder;
            ItemTienda itemTienda = listaItems.get(i);
            itemTiendaHolder.bindItem(itemTienda, listenerItem);
        }
    }

    @Override
    public int getItemCount() {
        return listaItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItems(ArrayList<ItemTienda> items) {
        listaItems.clear();
        listaItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        listaItems.clear();
        notifyDataSetChanged();
    }

}
