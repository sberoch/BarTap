package com.eriochrome.bartime.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.ItemTienda;

public class ItemTiendaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private ItemTienda itemTienda;

    private TextView descripcion;
    private TextView precio;

    public ItemTiendaHolder(@NonNull View view, Context context) {
        super(view);
        this.context = context;

        descripcion = view.findViewById(R.id.descripcion);
        precio = view.findViewById(R.id.precio);
    }

    public void bindItem(ItemTienda itemTienda) {
        this.itemTienda = itemTienda;

        descripcion.setText(itemTienda.getDescripcion());

        String textoPrecio = "Precio: " + String.valueOf(itemTienda.getCosto()) + " puntos.";
        precio.setText(textoPrecio);

    }

    @Override
    public void onClick(View v) {
        //TODO: dialog comprar (que muestre la resta tipo una compra en brawlhalla)
    }


}
