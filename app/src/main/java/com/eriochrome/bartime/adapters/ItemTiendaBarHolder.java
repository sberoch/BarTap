package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;

public class ItemTiendaBarHolder extends RecyclerView.ViewHolder {

    private Context context;
    private ItemTienda itemTienda;

    private TextView descripcion;
    private TextView precio;
    private ImageButton eliminarItem;
    private ImageButton editarItem;

    public interface HolderCallback {
        void onRemoveItem(ItemTienda itemTienda);
        void onEditItem(ItemTienda itemTienda);
    }

    public interface ListaListener {
        void sacarDeLista();
    }


    public ItemTiendaBarHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;

        descripcion = view.findViewById(R.id.descripcion);
        precio = view.findViewById(R.id.precio);
        eliminarItem = view.findViewById(R.id.eliminar_item);
        editarItem = view.findViewById(R.id.editar_item);
    }

    public void bindItem(ItemTienda itemTienda, HolderCallback callback, ListaListener listaListener) {
        this.itemTienda = itemTienda;

        descripcion.setText(itemTienda.getDescripcion());

        String textoPrecio = "Precio: " + String.valueOf(itemTienda.getCosto()) + " puntos.";
        precio.setText(textoPrecio);

        eliminarItem.setOnClickListener(v -> {
            listaListener.sacarDeLista();
            callback.onRemoveItem(itemTienda);
        });

        editarItem.setOnClickListener(v -> {
            callback.onEditItem(itemTienda);
        });
    }
}
