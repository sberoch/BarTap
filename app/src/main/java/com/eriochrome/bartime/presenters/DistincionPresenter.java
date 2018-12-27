package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.DistincionContract;
import com.eriochrome.bartime.modelos.DistincionInteraccion;

public class DistincionPresenter implements DistincionContract.CompleteListener{

    private DistincionContract.Interaccion interaccion;
    private DistincionContract.View view;
    private boolean esNuevo;

    public DistincionPresenter() {
        this.interaccion = new DistincionInteraccion(this);
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

    public boolean esNuevoUsuario() {
        interaccion.checkearEsNuevo();
        return esNuevo;
    }

    @Override
    public void checkearNuevo(boolean esNuevo) {
        this.esNuevo = esNuevo;
    }
}
