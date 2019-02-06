package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.PaginaJuegoContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Desafio;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.utils.ActualizadorFirebase;
import com.eriochrome.bartime.utils.CreadorDeAvisos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PaginaJuegoInteraccion implements PaginaJuegoContract.Interaccion {

    private Juego juego;
    private PaginaJuegoContract.Listener listener;
    private DatabaseReference refGlobal;
    private DatabaseReference refParticipantes;
    private ArrayList<String> participantes;
    private Bar bar;

    public PaginaJuegoInteraccion(PaginaJuegoContract.Listener listener) {
        this.listener = listener;
        refGlobal = FirebaseDatabase.getInstance().getReference();
        refParticipantes = refGlobal.child("juegos");
        participantes = new ArrayList<>();
    }

    @Override
    public void setJuego(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public String getTipoDeJuego() {
        return juego.getTipoDeJuego();
    }

    @Override
    public String getResumenJuego() {
        return juego.getTextoResumen();
    }

    @Override
    public void obtenerJuegos() {
        refParticipantes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dsJuego : dataSnapshot.child("Desafio").getChildren()) {
                    Juego juego = dsJuego.getValue(Desafio.class);
                    if (juego.getID().equals(dsJuego.getKey())) {
                        for (DataSnapshot dsParticipante : dsJuego.child("participantes").getChildren()) {
                            participantes.add(dsParticipante.getValue(String.class));
                        }
                    }
                }
                listener.onComplete(participantes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public boolean esUnJuegoValidable() {
        return juego.getTipoDeJuego().equals("Desafio");
    }

    @Override
    public void declararGanador(String ganador) {
        DatabaseReference refJuego =  refGlobal.child("juegos")
                .child(juego.getTipoDeJuego()).child(juego.getID());

        //Sumarle puntos al ganador
        ActualizadorFirebase.actualizarPuntos(ganador, bar, juego.getPuntos(), refGlobal);

        //Remover al usuario si es permantente, sino remover el juego
        if (!esJuegoPermanente()) {
            refJuego.removeValue();
        } else {
           refJuego.child("participantes").child(ganador).removeValue();
        }

        //Avisarle al usuario que gano
        CreadorDeAvisos creadorDeAvisos = new CreadorDeAvisos();
        creadorDeAvisos.avisarGanadorDeJuego(juego, ganador);

    }

    private boolean esJuegoPermanente() {
        switch (juego.getTipoDeJuego()) {
            case "Desafio":
                return ((Desafio)juego).isPermanente();

            default:
                return false;
        }
    }
}