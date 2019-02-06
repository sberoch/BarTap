package com.eriochrome.bartime.presenters;

import android.net.Uri;

import com.eriochrome.bartime.contracts.AgregarBarUsuarioContract;
import com.eriochrome.bartime.modelos.AgregarBarUsuarioInteraccion;


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

    public void agregarImagen(Uri path) {
        interaccion.agregarImagen(path);
    }

    public void agregarUbicacion(String direccion, double lat, double lng) {
        interaccion.agregarUbicacion(direccion, lat, lng);
    }

    public void crearBar(String nombreBar) {
        interaccion.crearBar(nombreBar);
    }

    public void subirBar() {
        interaccion.subirBar();
        view.startConfirmacion();
    }

    public boolean datosCompletos() {
        return view.hayImagenSeleccionada() &&
               view.hayUbicacionSeleccionada() &&
               view.hayNombreValido();
    }
}
