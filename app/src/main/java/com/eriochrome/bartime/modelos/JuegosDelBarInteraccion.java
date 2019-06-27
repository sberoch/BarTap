package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.JuegosDelBarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Desafio;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.eriochrome.bartime.presenters.JuegosDelBarPresenter;
import com.eriochrome.bartime.utils.CreadorDeAvisos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JuegosDelBarInteraccion implements JuegosDelBarContract.Interaccion {

    private JuegosDelBarContract.Listener listener;
    private DatabaseReference ref;
    private Bar bar;
    private ArrayList<Juego> juegos;
    private FirebaseAuth auth;


    public JuegosDelBarInteraccion(JuegosDelBarContract.Listener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        juegos = new ArrayList<>();
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void mostrarJuegos() {
        ref.child("juegos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("Desafio").getChildren()) {
                    Juego juego = ds.getValue(Desafio.class);
                    if (juego.getNombreBar().equals(bar.getNombre())) {
                        juegos.add(juego);
                    }
                }
                for (DataSnapshot ds : dataSnapshot.child("Trivia").getChildren()) {
                    Juego juego = ds.getValue(Trivia.class);
                    if (juego.getNombreBar().equals(bar.getNombre())) {
                        juegos.add(juego);
                    }
                }
                listener.onJuegosCargados(juegos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public boolean estaConectado() {
        return auth.getCurrentUser() != null;
    }

    /**
     * Chequea si se participo previamente en el juego.
     */
    @Override
    public void intentarParticiparDeJuego(Juego juego) {
        ref.child("juegos").child(juego.getTipoDeJuego()).child(juego.getID()).child("participantes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(auth.getCurrentUser().getDisplayName())) {
                            listener.yaSeParticipo();
                        } else {
                            listener.participarDeJuego(juego);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    @Override
    public void participarDeJuego(Juego juego) {
        ref.child("juegos").child(juego.getTipoDeJuego()).child(juego.getID()).child("participantes")
                .child(auth.getCurrentUser().getDisplayName()).setValue(auth.getCurrentUser().getDisplayName())
                .addOnSuccessListener(aVoid -> {

                    if (juego.getTipoDeJuego().equals("Trivia")) {
                        listener.ingresarATrivia((Trivia)juego);
                    } else {
                        listener.successParticipando();

                    }
                });

        avisarParticipacion(juego);
    }

    private void avisarParticipacion(Juego juego) {
        CreadorDeAvisos creadorDeAvisos = new CreadorDeAvisos();
        creadorDeAvisos.avisarParticipacion(auth.getCurrentUser().getDisplayName(), juego);
    }
}