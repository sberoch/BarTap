package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.modelos.entidades.PreguntaTrivia;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.eriochrome.bartime.utils.ActualizadorFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;


public class TriviaInteraccion implements TriviaContract.Interaccion {

    private Trivia trivia;
    private DatabaseReference ref;
    private FirebaseUser authuser;
    private int preguntasRestantes;
    private int numeroDePreguntaActual;

    public TriviaInteraccion() {
        ref = FirebaseDatabase.getInstance().getReference();
        authuser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
        preguntasRestantes = trivia.getCantPreguntas();
        numeroDePreguntaActual = 0;
    }

    @Override
    public PreguntaTrivia cargarSiguiente() {
        PreguntaTrivia siguiente = trivia.getPreguntas().get(numeroDePreguntaActual);
        preguntasRestantes--;
        numeroDePreguntaActual++;
        return siguiente;
    }

    @Override
    public boolean eligioOpcionCorrecta(String opcion) {
        PreguntaTrivia preguntaTrivia =  trivia.getPreguntas().get(numeroDePreguntaActual - 1);
        boolean esCorrecta = false;
        if (opcion.equals(preguntaTrivia.getOpcionA()) && preguntaTrivia.getOpcionCorrecta().equals("A")) {
            esCorrecta = true;
        }
        if (opcion.equals(preguntaTrivia.getOpcionB()) && preguntaTrivia.getOpcionCorrecta().equals("B")) {
            esCorrecta = true;
        }
        if (opcion.equals(preguntaTrivia.getOpcionC()) && preguntaTrivia.getOpcionCorrecta().equals("C")) {
            esCorrecta = true;
        }
        return esCorrecta;
    }

    @Override
    public void actualizarPuntos() {
        ActualizadorFirebase.actualizarPuntos(authuser.getDisplayName(), trivia.getNombreBar(), trivia.getPuntos(), ref);
    }

    @Override
    public boolean quedanPreguntas() {
        return preguntasRestantes > 0;
    }

    @Override
    public void agregarGanador() {
        ref.child("juegos").child("Trivia").child(trivia.getID()).child("cantGanadores").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Integer ganadoresActuales = mutableData.getValue(Integer.class);
                if (ganadoresActuales == null) {
                    mutableData.setValue(1);
                }
                else {
                    mutableData.setValue(ganadoresActuales + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {

            }
        });
    }
}