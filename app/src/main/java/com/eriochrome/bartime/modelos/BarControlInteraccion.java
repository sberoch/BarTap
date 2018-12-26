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
    private BarControlContract.CompleteListener listener;

    public BarControlInteraccion(BarControlContract.CompleteListener listener) {
        this.listener = listener;
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        barUsuarioRef = FirebaseDatabase.getInstance().getReference().child("usuariosBar").child(userAuth.getUid());
    }


    @Override
    public void hayBarAsociado() {
        barUsuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean hayBar = dataSnapshot.hasChild("barAsociado");
                listener.onComplete(hayBar);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}