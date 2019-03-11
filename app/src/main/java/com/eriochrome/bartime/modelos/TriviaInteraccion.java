package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.modelos.entidades.PreguntaTrivia;
import com.eriochrome.bartime.modelos.entidades.Trivia;


public class TriviaInteraccion implements TriviaContract.Interaccion {

    private Trivia trivia;

    @Override
    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
    }

    @Override
    public PreguntaTrivia cargarPrimeraPregunta() {
        return trivia.getPreguntas().get(0);
    }

    @Override
    public boolean eligioOpcionCorrecta(String opcion) {
        PreguntaTrivia preguntaTrivia =  trivia.getPreguntas().get(0);
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
}