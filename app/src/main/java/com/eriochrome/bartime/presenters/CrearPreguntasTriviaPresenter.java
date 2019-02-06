package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.CrearPreguntasTriviaContract;
import com.eriochrome.bartime.modelos.CrearPreguntasTriviaInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.PreguntaTrivia;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public class CrearPreguntasTriviaPresenter implements CrearPreguntasTriviaContract.Listener{

    private CrearPreguntasTriviaContract.Interaccion interaccion;
    private CrearPreguntasTriviaContract.View view;

    public CrearPreguntasTriviaPresenter() {
        interaccion = new CrearPreguntasTriviaInteraccion(this);
    }

    public void bind(CrearPreguntasTriviaContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public void obtenerTrivia(Intent intent) {
        Trivia trivia = (Trivia) intent.getSerializableExtra("trivia");
        interaccion.setTrivia(trivia);
    }

    public void checkearPreguntasRestantes() {
        if (!interaccion.quedanPreguntas()) {
            view.ultimaPregunta();
        }
    }

    public void guardarPregunta() {
        String pregunta = view.getPregunta();
        String opcionA = view.getOpcionA();
        String opcionB = view.getOpcionB();
        String opcionC = view.getOpcionC();
        String correcta = view.getRespuestaCorrecta();
        interaccion.guardarPregunta(pregunta, opcionA, opcionB, opcionC, correcta);
    }

    public void subirTrivia() {
        interaccion.subirTrivia();
    }

    @Override
    public void enviado() {
        view.enviado();
    }
}