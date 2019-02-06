package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;

public class ItemTiendaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private ItemTienda itemTienda;

    private TextView descripcion;
    private TextView precio;

    public interface ItemTiendaClickListener {
        void onClickItemTienda(ItemTienda itemTienda);
    }

    private ItemTiendaClickListener listener;

    public ItemTiendaHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;

        descripcion = view.findViewById(R.id.descripcion);
        precio = view.findViewById(R.id.precio);

        view.setOnClickListener(this);
    }

    public void bindItem(ItemTienda itemTienda, ItemTiendaClickListener listenerItem) {
        this.itemTienda = itemTienda;
        this.listener = listenerItem;

        descripcion.setText(itemTienda.getDescripcion());

        String textoPrecio = "Precio: " + String.valueOf(itemTienda.getCosto()) + " puntos.";
        precio.setText(textoPrecio);

    }

    @Override
    public void onClick(View v) {
        //TODO: dialog comprar (que muestre la resta tipo una compra en brawlhalla)
        listener.onClickItemTienda(itemTienda);
    }


}
