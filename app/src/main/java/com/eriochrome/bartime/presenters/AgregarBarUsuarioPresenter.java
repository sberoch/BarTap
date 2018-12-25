package com.eriochrome.bartime.presenters;

import android.net.Uri;

import com.eriochrome.bartime.contracts.AgregarBarUsuarioContract;
import com.eriochrome.bartime.modelos.AgregarBarUsuarioInteraccion;
import com.eriochrome.bartime.modelos.Bar;


public class AgregarBarUsuarioPresenter {

    private AgregarBarUsuarioContract.Interaccion interaccion;
    private AgregarBarUsuarioContract.View view;

    public AgregarBarUsuarioPresenter() {
        this.interaccion = new AgregarBarUsuarioInteraccion();
    }

    public void bind(AgregarBarUsuarioContract.View view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }

    public void agregarBar(String nombre, Uri path) {
        Bar bar = new Bar(nombre);
        if(view.hayImagenSeleccionada()){
            interaccion.agregarBar(bar, path);
            view.startConfirmacion();
        }
    }

}
