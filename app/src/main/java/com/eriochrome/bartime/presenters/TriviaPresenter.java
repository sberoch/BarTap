package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.modelos.TriviaInteraccion;
import com.eriochrome.bartime.modelos.entidades.PreguntaTrivia;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public class TriviaPresenter {

    private TriviaContract.Interaccion interaccion;
    private TriviaContract.View view;

    public TriviaPresenter() {
        interaccion = new TriviaInteraccion();
    }

    public void bind(TriviaContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerTrivia(Intent intent) {
        Trivia trivia = (Trivia) intent.getSerializableExtra("trivia");
        interaccion.setTrivia(trivia);
    }

    public void comenzarTrivia() {
        PreguntaTrivia primeraPregunta = interaccion.cargarPrimeraPregunta();
        String pregunta = primeraPregunta.getPregunta();
        String opA =  primeraPregunta.getOpcionA();
        String opB =  primeraPregunta.getOpcionB();
        String opC =  primeraPregunta.getOpcionC();
        view.llenar(pregunta, opA, opB, opC);
        //TODO: cargar preguntas en un ciclo

    }

    public boolean eligioOpcionCorrecta(String opcion) {
        return interaccion.eligioOpcionCorrecta(opcion);
    }
}