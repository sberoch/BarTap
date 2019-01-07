package com.eriochrome.bartime.presenters;

import android.content.Intent;
import android.net.Uri;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarOwnerContract;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.DatosBarOwnerInteraccion;

public class DatosBarOwnerPresenter implements DatosBarOwnerContract.CompleteListener {

    private DatosBarOwnerContract.Interaccion interaccion;
    private DatosBarOwnerContract.View view;
    private boolean seAgregoFotoNueva;
    private boolean esEditar;

    public DatosBarOwnerPresenter() {
        interaccion = new DatosBarOwnerInteraccion(this);
    }

    public void bind(DatosBarOwnerContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void listo() {
        interaccion.crearBar(view.getTextNombreBar());
        //TODO: ubicacion

        interaccion.getBar().agregarHorarios(view.getHorariosInicial(), view.getHorariosFinal());
        if (view.tieneHappyHour()) {
            interaccion.getBar().agregarHappyhourHorarios(view.getHappyhourInicial(), view.getHappyhourFinal());
        }

        interaccion.getBar().agregarMetodosDePago(view.obtenerMetodosDePago());

        if (view.hayImagen()) {
            if (esEditar) editar();
            else subir();
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

    private void editar() {
        try {
            if (seAgregoFotoNueva) {
                interaccion.subirFoto();
            }
            interaccion.editarBar();
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
        seAgregoFotoNueva = true;
    }

    /**
     * Si el usuario busca editar un bar existente, se pasa el bar y pone sus datos en los inputs
     */
    public void checkUsarDatosBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        if (bar != null) {
            setInputs(bar);
            view.setTitleEditar();
            esEditar = true;
        }
    }

    private void setInputs(Bar bar) {
        view.setNombreBar(bar.getNombre());
        view.yaTieneImagen();
        //TODO: set ubicacion
        view.setHorariosIniciales(bar.getHorariosInicial());
        view.setHorariosFinales(bar.getHorariosFinal());
        view.setHappyHourInicial(bar.getHorariosInicial());
        view.setHappyHourFinal(bar.getHorariosFinal());
        view.setMetodosDePago(bar.getMetodosDePago());
    }
}