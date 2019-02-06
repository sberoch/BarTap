package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.CrearTriviaContract;
import com.eriochrome.bartime.modelos.CrearTriviaInteraccion;

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

}