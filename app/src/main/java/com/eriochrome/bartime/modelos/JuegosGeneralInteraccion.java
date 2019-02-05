package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.JuegosGeneralContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JuegosGeneralInteraccion implements JuegosGeneralContract.Interaccion {

    private Bar bar;
    private JuegosGeneralContract.Listener listener;
    private DatabaseReference refJuegos;
    private ArrayList<Juego> juegos;

    public JuegosGeneralInteraccion(JuegosGeneralContract.Listener listener) {
        this.listener = listener;
        refJuegos = FirebaseDatabase.getInstance().getReference().child("juegos");
        juegos = new ArrayList<>();
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void mostrarJuegos() {
        refJuegos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //TODO: esta solo para desafio, agregar mas fors a medida que ponga nuevos tipos de juego
                for (DataSnapshot ds : dataSnapshot.child("Desafio").getChildren()) {
                    Juego juego = ds.getValue(Desafio.class);
                    if (juego.getNombreBar().equals(bar.getNombre())) {
                        juegos.add(juego);
                    }
                }
                listener.listo(juegos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public Bar getBar() {
        return bar;
    }
}