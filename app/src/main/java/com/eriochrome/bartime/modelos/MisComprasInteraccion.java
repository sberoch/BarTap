package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.MisComprasContract;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MisComprasInteraccion implements MisComprasContract.Interaccion {

    private MisComprasContract.Listener listener;
    private FirebaseUser authUser;
    private DatabaseReference ref;
    private ArrayList<ComprobanteDeCompra> compras;

    public MisComprasInteraccion(MisComprasContract.Listener listener) {
        this.listener = listener;
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("comprobantesDeCompra");
        compras = new ArrayList<>();
    }

    @Override
    public void cargarCompras() {
        ref.child(authUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsBares : dataSnapshot.getChildren()) {
                    for (DataSnapshot dsComprobante : dsBares.getChildren()) {
                        ComprobanteDeCompra comprobanteDeCompra = dsComprobante.getValue(ComprobanteDeCompra.class);
                        compras.add(comprobanteDeCompra);
                    }
                }
                listener.listo(compras);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}