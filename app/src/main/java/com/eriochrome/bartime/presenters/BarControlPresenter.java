package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.modelos.BarControlInteraccion;

public class BarControlPresenter {

    private BarControlContract.Interaccion interaccion;
    private BarControlContract.View view;

    public BarControlPresenter() {
        interaccion = new BarControlInteraccion();
    }

    public void bind(BarControlContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}