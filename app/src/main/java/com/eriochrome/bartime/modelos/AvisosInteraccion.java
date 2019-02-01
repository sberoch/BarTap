package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.AvisosContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvisosInteraccion implements AvisosContract.Interaccion {

    private AvisosContract.Listener listener;
    private ArrayList<Aviso> avisos;
    private DatabaseReference refAvisos;
    private FirebaseUser authUser;

    public AvisosInteraccion(AvisosContract.Listener listener) {
        this.listener = listener;
        avisos = new ArrayList<>();
        refAvisos = FirebaseDatabase.getInstance().getReference().child("avisos");
        authUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void cargarAvisos() {
        refAvisos.child(authUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String avisoTexto = ds.getValue(String.class);
                    String avisoID = ds.getKey();
                    avisos.add(new Aviso(avisoTexto, avisoID));
                }
                listener.listo(avisos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void quitarItem(String idItem) {
        refAvisos.child(authUser.getDisplayName()).child(idItem).setValue(null);
    }
}