package com.eriochrome.bartime.presenters;


import com.eriochrome.bartime.contracts.LauncherContract;
import com.eriochrome.bartime.modelos.LauncherInteraccion;

public class LauncherPresenter {

    private LauncherContract.Interaccion interaccion;
    private LauncherContract.View view;

    public LauncherPresenter() {
        this.interaccion = new LauncherInteraccion();
    }

    public void bind(LauncherContract.View view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }

    public void redirigir() {
        if (interaccion.estaConectado()) {
            if(interaccion.esBar()) {
                view.startBar();
            } else {
                view.startUsuario();
            }
        } else {
            view.startNuevo();
        }
    }
}
