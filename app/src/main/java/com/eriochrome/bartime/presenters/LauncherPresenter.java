package com.eriochrome.bartime.presenters;


import com.eriochrome.bartime.contracts.LauncherContract;
import com.eriochrome.bartime.modelos.LauncherInteraccion;

public class LauncherPresenter implements LauncherContract.CompleteListener{

    private LauncherContract.Interaccion interaccion;
    private LauncherContract.View view;

    public LauncherPresenter() {
        this.interaccion = new LauncherInteraccion(this);
    }

    public void bind(LauncherContract.View view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }

    public void redirigir() {
        if (interaccion.estaConectado()) {
            interaccion.esBar();
        } else {
            if (view != null)
                view.startNuevo();
        }
    }

    @Override
    public void esBar(boolean esBar) {
        if (view != null) {
            if (esBar) {
                view.startBar();
            } else {
                view.startUsuario();
            }
        }
    }
}
