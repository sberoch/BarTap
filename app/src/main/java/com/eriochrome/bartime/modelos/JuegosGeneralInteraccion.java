package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.JuegosGeneralContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Desafio;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Sorteo;
import com.eriochrome.bartime.modelos.entidades.Trivia;
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
                for (DataSnapshot ds : dataSnapshot.child("Sorteo").getChildren()) {
                    Juego juego = ds.getValue(Sorteo.class);
                    if (juego.getNombreBar().equals(bar.getNombre())) {
                        juegos.add(juego);
                    }
                }
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

    @Override
    public boolean esSorteo(Juego juego) {
        return juego.getTipoDeJuego().equals("Sorteo");
    }

    @Override
    public boolean esTrivia(Juego juego) {
        return juego.getTipoDeJuego().equals("Trivia");
    }
}