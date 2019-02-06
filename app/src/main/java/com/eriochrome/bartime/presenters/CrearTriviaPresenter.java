package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.CrearTriviaContract;
import com.eriochrome.bartime.modelos.CrearTriviaInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

public class CrearTriviaPresenter {

    private CrearTriviaContract.Interaccion interaccion;
    private CrearTriviaContract.View view;

    public CrearTriviaPresenter() {
        interaccion = new CrearTriviaInteraccion();
    }

    public void bind(CrearTriviaContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }

    public Intent enviarTrivia(Intent i) {
        return i.putExtra("trivia", interaccion.getTrivia());
    }

    public void comenzarCreacionTrivia() {
        String titulo = view.getTitulo();
        int cantPreguntas = view.getCantPreguntas();
        interaccion.comenzarCreacionTrivia(titulo, cantPreguntas);

    }
}