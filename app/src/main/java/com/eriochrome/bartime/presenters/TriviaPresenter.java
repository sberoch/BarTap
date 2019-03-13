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

    public boolean eligioOpcionCorrecta(String opcion) {
        return interaccion.eligioOpcionCorrecta(opcion);
    }

    public void actualizarPuntos() {
        interaccion.actualizarPuntos();
    }

    public boolean quedanPreguntas() {
        return interaccion.quedanPreguntas();
    }

    public void cargarSiguientePregunta() {
        PreguntaTrivia preguntaTrivia = interaccion.cargarSiguiente();
        String pregunta = preguntaTrivia.getPregunta();
        String opA =  preguntaTrivia.getOpcionA();
        String opB =  preguntaTrivia.getOpcionB();
        String opC =  preguntaTrivia.getOpcionC();
        view.llenar(pregunta, opA, opB, opC);
    }

    public void agregarGanador() {
        interaccion.agregarGanador();
    }
}