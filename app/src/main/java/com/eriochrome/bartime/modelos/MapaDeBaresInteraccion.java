package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.MapaDeBaresContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MapaDeBaresInteraccion implements MapaDeBaresContract.Interaccion {

    private MapaDeBaresContract.Listener listener;
    private DatabaseReference ref;

    public MapaDeBaresInteraccion(MapaDeBaresContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference().child("bares");
    }

    @Override
    public void getPosicionesDeBares() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bar bar = ds.getValue(Bar.class);
                    listener.marcarBar(bar);
                }
                listener.listo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}