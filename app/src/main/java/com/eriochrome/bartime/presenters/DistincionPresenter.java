package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.DistincionContract;
import com.eriochrome.bartime.modelos.DistincionInteraccion;

public class DistincionPresenter {

    private DistincionContract.Interaccion interaccion;
    private DistincionContract.View view;

    public DistincionPresenter() {
        this.interaccion = new DistincionInteraccion();
    }

    public void bind(DistincionContract.View view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }

    public void subirUsuarioADatabase() {
        interaccion.subirUsuarioBarADatabase();
    }
}
