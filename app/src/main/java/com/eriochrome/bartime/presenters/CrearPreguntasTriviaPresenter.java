package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.CrearPreguntasTriviaContract;
import com.eriochrome.bartime.modelos.CrearPreguntasTriviaInteraccion;

public class CrearPreguntasTriviaPresenter {

    private CrearPreguntasTriviaContract.Interaccion interaccion;
    private CrearPreguntasTriviaContract.View view;

    public CrearPreguntasTriviaPresenter() {
        interaccion = new CrearPreguntasTriviaInteraccion();
    }

    public void bind(CrearPreguntasTriviaContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}