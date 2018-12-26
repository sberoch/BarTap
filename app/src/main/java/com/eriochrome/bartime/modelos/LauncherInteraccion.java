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
    private DatabaseReference refUsuariosBar;
    private LauncherContract.CompleteListener listener;

    public LauncherInteraccion(LauncherContract.CompleteListener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
        refUsuariosBar = FirebaseDatabase.getInstance().getReference().child("usuariosBar");
    }

    @Override
    public boolean estaConectado() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public void esBar() {
        refUsuariosBar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(auth.getCurrentUser().getUid())) {
                    listener.esBar(true);
                } else {
                    listener.esBar(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
