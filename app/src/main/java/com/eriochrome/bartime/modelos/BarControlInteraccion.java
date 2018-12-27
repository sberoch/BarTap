package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BarControlInteraccion implements BarControlContract.Interaccion {

    private FirebaseUser userAuth;
    private DatabaseReference barUsuarioRef;
    private DatabaseReference refGlobal;
    private BarControlContract.CompleteListener listener;

    private Bar bar;

    public BarControlInteraccion(BarControlContract.CompleteListener listener) {
        this.listener = listener;
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        refGlobal = FirebaseDatabase.getInstance().getReference();
        barUsuarioRef = refGlobal.child("usuariosBar").child(userAuth.getUid());
    }

    @Override
    public void setupBar() {
        listener.onStart();
        barUsuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("barAsociado").child("nombre").exists()) {
                    bar = dataSnapshot.child("barAsociado").getValue(Bar.class);
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
    public void mockearBar() {
        barUsuarioRef.child("barAsociado").setValue(new Bar("Testeando"));
    }

}