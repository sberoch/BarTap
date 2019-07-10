package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.TiendaBarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TiendaBarInteraccion implements TiendaBarContract.Interaccion {

    private TiendaBarContract.Listener listener;
    private DatabaseReference ref;
    private ArrayList<ItemTienda> itemsTienda;
    private Bar bar;

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            itemsTienda.clear();
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                ItemTienda itemTienda = ds.getValue(ItemTienda.class);
                itemsTienda.add(itemTienda);
            }
            listener.listo(itemsTienda);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    public TiendaBarInteraccion(TiendaBarContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference();
        itemsTienda = new ArrayList<>();
    }

    @Override
    public void mostrarItemsTienda() {
        ref.child("tiendas").child(bar.getNombre()).addValueEventListener(valueEventListener);
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void crearItem(ItemTienda itemTienda) {
        String key = ref.child("tiendas").child(bar.getNombre()).push().getKey();
        if (key != null) {
            itemTienda.setID(key);
            ref.child("tiendas").child(bar.getNombre()).child(key).setValue(itemTienda);
        }
    }

    @Override
    public void eliminarItem(ItemTienda itemTienda) {
        ref.child("tiendas").child(bar.getNombre()).child(itemTienda.getID()).removeValue();
    }

    @Override
    public void dejarDeMostrarItems() {
        ref.child("tiendas").child(bar.getNombre()).removeEventListener(valueEventListener);
    }

    @Override
    public Bar getBar() {
        return bar;
    }
}