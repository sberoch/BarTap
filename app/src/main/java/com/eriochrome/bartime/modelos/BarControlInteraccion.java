package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarControlInteraccion implements BarControlContract.Interaccion {

    private FirebaseUser userAuth;
    private DatabaseReference refGlobal;
    private BarControlContract.CompleteListener listener;

    private Bar bar;

    public BarControlInteraccion(BarControlContract.CompleteListener listener) {
        this.listener = listener;
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        refGlobal = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void setupBar() {
        listener.onStart();
        refGlobal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot barAsociadoPath = dataSnapshot.child("usuariosBar").child(userAuth.getUid()).child("barAsociado");
                if (barAsociadoPath.exists()) {
                    String nombreBarAsociado = barAsociadoPath.getValue(String.class);
                    bar = dataSnapshot.child("bares").child(nombreBarAsociado).getValue(Bar.class);
                    listener.onComplete(bar);
                } else {
                    listener.onComplete(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public String getNombreBar() {
        return bar.getNombre();
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void crearOferta(String oferta, String fechafinal) {

        refGlobal.child("bares").child(bar.getNombre()).child("oferta").setValue(oferta)
        .addOnSuccessListener(aVoid -> listener.finCrearOferta());
        //refGlobal.child("bares").child(bar.getNombre()).child("fechaFinalOferta").setValue(fechafinal)
        //.addOnSuccessListener(aVoid -> listener.finCrearOferta()); TODO: ponerlo en un solo lugar
    }

}