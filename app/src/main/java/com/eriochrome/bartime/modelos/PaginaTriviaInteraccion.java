package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.PaginaTriviaContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class PaginaTriviaInteraccion implements PaginaTriviaContract.Interaccion {

    private Trivia trivia;
    private PaginaTriviaContract.Listener listener;
    private DatabaseReference ref;

    public PaginaTriviaInteraccion(PaginaTriviaContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference().child("juegos").child("Trivia");
    }

    @Override
    public void setTrivia(Juego juego) {
        this.trivia = (Trivia) juego;
    }

    @Override
    public void cargarDatosParticipantes() {
        ref.child(trivia.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int cantParticipantes = (int) dataSnapshot.child("participantes").getChildrenCount();
                int cantGanadores;
                try {
                    cantGanadores = dataSnapshot.child("cantGanadores").getValue(Integer.class);
                } catch (NullPointerException e) {
                    cantGanadores = 0;
                }
                listener.onComplete(cantParticipantes, cantGanadores);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public String getTipoDeJuego() {
        return trivia.getTipoDeJuego();
    }

    @Override
    public String getResumen() {
        return trivia.getTextoResumen();
    }
}