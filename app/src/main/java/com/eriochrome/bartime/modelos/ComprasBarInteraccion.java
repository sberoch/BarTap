package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.ComprasBarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComprasBarInteraccion implements ComprasBarContract.Interaccion {

    private Bar bar;
    private ComprasBarContract.Listener listener;
    private DatabaseReference ref;
    private ArrayList<ComprobanteDeCompra> compras;

    public ComprasBarInteraccion(ComprasBarContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference().child("comprobantesDeCompra");
        compras = new ArrayList<>();
    }

    @Override
    public void cargarCompras() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsUsuario : dataSnapshot.getChildren()) {
                    if (dsUsuario.hasChild(bar.getNombre())) {
                        for (DataSnapshot dsComprobante : dsUsuario.child(bar.getNombre()).getChildren()) {
                            ComprobanteDeCompra comprobante = dsComprobante.getValue(ComprobanteDeCompra.class);
                            compras.add(comprobante);
                        }
                    }
                }
                listener.listo(compras);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void eliminarComprobante(ComprobanteDeCompra comprobante) {
        ref.child(comprobante.getNombreUsuario()).child(comprobante.getNombreBar())
                .child(String.valueOf(comprobante.getNroComprobante())).setValue(null);
    }
}