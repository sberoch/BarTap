package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.EditarBarContract;
import com.eriochrome.bartime.modelos.EditarBarInteraccion;

public class EditarBarPresenter {

    private EditarBarContract.Interaccion interaccion;
    private EditarBarContract.View view;

    public EditarBarPresenter() {
        interaccion = new EditarBarInteraccion();
    }

    public void bind(EditarBarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}