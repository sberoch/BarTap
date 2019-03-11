package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.modelos.TriviaInteraccion;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public class TriviaPresenter implements TriviaContract.Listener {

    private TriviaContract.Interaccion interaccion;
    private TriviaContract.View view;

    public TriviaPresenter() {
        interaccion = new TriviaInteraccion(this);
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
        view.cargando();
        //TODO: cargar preguntas
    }
}