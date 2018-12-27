package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.modelos.BarControlInteraccion;

public class BarControlPresenter implements BarControlContract.CompleteListener {

    private BarControlContract.Interaccion interaccion;
    private BarControlContract.View view;
    private boolean hayBarAsociado;

    public BarControlPresenter() {
        interaccion = new BarControlInteraccion(this);
        hayBarAsociado = false;
    }

    public void bind(BarControlContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public boolean hayBarAsociado() {
        interaccion.hayBarAsociado();
        return hayBarAsociado;
    }

    @Override
    public void onComplete(boolean hayBar) {
        hayBarAsociado = hayBar;
    }

    //TODO: mock
    public void mockBarCreado() {
        hayBarAsociado = true;
    }
}