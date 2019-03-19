package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.MisComprasContract;
import com.eriochrome.bartime.modelos.MisComprasInteraccion;

public class MisComprasPresenter {

    private MisComprasContract.Interaccion interaccion;
    private MisComprasContract.View view;

    public MisComprasPresenter() {
        interaccion = new MisComprasInteraccion();
    }

    public void bind(MisComprasContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}