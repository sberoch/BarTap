package com.eriochrome.bartime.modelos;


import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.LauncherContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LauncherInteraccion implements LauncherContract.Interaccion {

    private FirebaseAuth auth;
    private DatabaseReference refUsuarios;

    public LauncherInteraccion() {
        auth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference().child("usuarios");
    }

    @Override
    public boolean estaConectado() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public boolean esBar() {
        final boolean[] esBar = new boolean[1];
        String userID = auth.getCurrentUser().getUid();
        Query query = refUsuarios.child(userID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                esBar[0] = dataSnapshot.getValue(Usuario.class).esBar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO: throw?
            }
        });
        return esBar[0];
    }
}
