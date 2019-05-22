package com.eriochrome.bartime.presenters;

import android.content.Intent;
import android.net.Uri;

import com.eriochrome.bartime.contracts.DatosBarOwnerContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.DatosBarOwnerInteraccion;

public class DatosBarOwnerPresenter implements DatosBarOwnerContract.CompleteListener {

    private DatosBarOwnerContract.Interaccion interaccion;
    private DatosBarOwnerContract.View view;
    private boolean seAgregoFotoNueva;
    private boolean esEditar;
    private boolean hp;

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
        interaccion.setUbicacion(view.getDireccion(), view.getLat(), view.getLng());
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
        view.setUbicacion(bar.getUbicacion());
        view.setHorarios(bar.getHorariosInicial(), bar.getHorariosFinal());
        view.setHappyHours(bar.getHappyhourInicial(), bar.getHappyhourFinal());
        view.setMetodosDePago(bar.getMetodosDePago());
    }

    public void setUbicacion(Intent data) {
        double lat = data.getDoubleExtra("latitud", 0);
        double lng = data.getDoubleExtra("longitud", 0);
        String direccion = data.getStringExtra("direccion");
        interaccion.setUbicacion(direccion, lat, lng);
    }

    public String getDireccion() {
        return interaccion.getDireccion();
    }
}