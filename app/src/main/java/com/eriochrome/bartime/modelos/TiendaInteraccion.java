package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.TiendaContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.eriochrome.bartime.utils.CreadorDeAvisos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TiendaInteraccion implements TiendaContract.Interaccion {

    private Bar bar;
    private DatabaseReference ref;
    private FirebaseUser authUser;
    private ArrayList<ItemTienda> itemsTienda;
    private TiendaContract.Listener listener;
    private int misPuntos;

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            itemsTienda.clear();

            DataSnapshot snapItems = dataSnapshot.child("tiendas").child(bar.getNombre());
            for (DataSnapshot ds : snapItems.getChildren()) {
                ItemTienda itemTienda = ds.getValue(ItemTienda.class);
                itemsTienda.add(itemTienda);
            }

            DataSnapshot snapPuntos = dataSnapshot.child("puntos").child(authUser.getDisplayName()).child(bar.getNombre());
            Integer puntos;
            if (snapPuntos.exists()) {
                puntos = snapPuntos.getValue(Integer.class);
            } else {
                puntos = 0;
            }

            listener.listo(itemsTienda, puntos);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    public TiendaInteraccion(TiendaContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference();
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        itemsTienda = new ArrayList<>();
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void setupTienda() {
        ref.addValueEventListener(valueEventListener);
    }

    @Override
    public void guardarPuntos(Integer misPuntos) {
        this.misPuntos = misPuntos;
    }

    @Override
    public int getPuntos() {
        return misPuntos;
    }

    @Override
    public void comprarItem(ItemTienda itemTienda) {
        misPuntos -= itemTienda.getCosto();
        ref.child("puntos").child(authUser.getDisplayName()).child(bar.getNombre()).setValue(misPuntos);

        ComprobanteDeCompra comprobante = new ComprobanteDeCompra(itemTienda, bar.getNombre(), authUser.getDisplayName());
        ref.child("comprobantesDeCompra").child(authUser.getDisplayName()).child(bar.getNombre())
                .child(String.valueOf(comprobante.getNroComprobante())).setValue(comprobante);

        CreadorDeAvisos creadorDeAvisos = new CreadorDeAvisos();
        creadorDeAvisos.avisarCompraDeDescuento(itemTienda, authUser.getDisplayName(), bar);
    }

    @Override
    public void dejarDeEscucharCambios() {
        ref.removeEventListener(valueEventListener);
    }
}