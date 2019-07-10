package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.MisJuegosContract;
import com.eriochrome.bartime.modelos.entidades.Desafio;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MisJuegosInteraccion implements MisJuegosContract.Interaccion {

    private MisJuegosContract.Listener listener;
    private DatabaseReference ref;
    private FirebaseUser authUser;
    private ArrayList<Juego> juegos;

    public MisJuegosInteraccion(MisJuegosContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference().child("juegos");
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        juegos = new ArrayList<>();
    }

    //Doble for, medio feo
    @Override
    public void mostrarJuegos() {
        String nombreUsuario = authUser.getDisplayName();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dsJuego : dataSnapshot.child("Desafio").getChildren()) {
                    Juego juego = dsJuego.getValue(Desafio.class);
                    for (DataSnapshot dsParticipante : dsJuego.child("participantes").getChildren()) {
                        String participante = dsParticipante.getValue(String.class);
                        if (nombreUsuario.equals(participante)) {
                            juegos.add(juego);
                        }
                    }
                }

                //No lo hago para trivia porque no se puede "Dejar de participar", es de una sola vez
                //Tampoco tiene sentido guardarla como juego que se esta jugando ahora

                listener.listo(juegos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void dejarDeParticipar(Juego juego) {
        ref.child(juego.getTipoDeJuego()).child(juego.getID())
                .child("participantes").child(authUser.getDisplayName())
                .setValue(null);
    }
}