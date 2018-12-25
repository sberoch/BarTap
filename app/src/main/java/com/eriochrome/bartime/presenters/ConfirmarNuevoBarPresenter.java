package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.ConfirmarNuevoBarContract;
import com.eriochrome.bartime.modelos.ConfirmarNuevoBarInteraccion;

public class ConfirmarNuevoBarPresenter {

    private ConfirmarNuevoBarContract.Interaccion interaccion;
    private ConfirmarNuevoBarContract.View view;

    public ConfirmarNuevoBarPresenter() {
        interaccion = new ConfirmarNuevoBarInteraccion();
    }

    public void bind(ConfirmarNuevoBarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}