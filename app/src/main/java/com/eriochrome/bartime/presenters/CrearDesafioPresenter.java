package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.CrearDesafioContract;
import com.eriochrome.bartime.modelos.CrearDesafioInteraccion;

public class CrearDesafioPresenter {

    private CrearDesafioContract.Interaccion interaccion;
    private CrearDesafioContract.View view;

    public CrearDesafioPresenter() {
        interaccion = new CrearDesafioInteraccion();
    }

    public void bind(CrearDesafioContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}