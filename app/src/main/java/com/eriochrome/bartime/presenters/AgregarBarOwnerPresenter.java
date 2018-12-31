package com.eriochrome.bartime.presenters;

import android.net.Uri;

import com.eriochrome.bartime.contracts.AgregarBarOwnerContract;
import com.eriochrome.bartime.modelos.AgregarBarOwnerInteraccion;

public class AgregarBarOwnerPresenter implements AgregarBarOwnerContract.CompleteListener {

    private AgregarBarOwnerContract.Interaccion interaccion;
    private AgregarBarOwnerContract.View view;

    public AgregarBarOwnerPresenter() {
        interaccion = new AgregarBarOwnerInteraccion(this);
    }

    public void bind(AgregarBarOwnerContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void listo() {
        interaccion.crearBar(view.getTextNombreBar());
        //TODO: ubicacion
        interaccion.getBar().agregarHorarios(view.getHorarioInicial(), view.getHorarioFinal());
        if (view.tieneHappyHour()){
            interaccion.getBar().agregarHappyhourHorarios(view.getHappyhourInicial(), view.getHappyhourFinal());
        }
        interaccion.getBar().agregarMetodosDePago(view.obtenerMetodosDePago());
        if (view.hayImagen()) {
            subir();
        } else {
            view.noHayImagenError();
        }
    }

    private void subir() {
        try {
            interaccion.subirFoto();
            interaccion.subirBar();
        } catch (RuntimeException e) {
            view.mostrarError();
        }
    }

    @Override
    public void onStart() {
        view.subiendo();
    }

    @Override
    public void onSuccess() {
        view.finSubiendo();
    }

    public void agregarFoto(Uri path) {
        interaccion.agregarFoto(path);
    }
}