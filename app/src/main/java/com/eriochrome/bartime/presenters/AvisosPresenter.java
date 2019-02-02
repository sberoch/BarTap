package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.AvisosContract;
import com.eriochrome.bartime.modelos.Aviso;
import com.eriochrome.bartime.modelos.AvisosInteraccion;
import com.eriochrome.bartime.vistas.AvisosActivity;

import java.util.ArrayList;

public class AvisosPresenter implements AvisosContract.Listener {

    private AvisosContract.Interaccion interaccion;
    private AvisosContract.View view;

    public AvisosPresenter() {
        interaccion = new AvisosInteraccion(this);
    }

    public void bind(AvisosContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void cargarAvisos(boolean esBar) {
        view.cargando();
        interaccion.cargarAvisos(esBar);
    }

    @Override
    public void listo(ArrayList<Aviso> avisos) {
        view.finCargando(avisos);
    }

    @Override
    public void quitarItemBar(String idItem, String nombreBar) {
        interaccion.quitarConNombreBar(idItem, nombreBar);
    }

    public void quitarItem(String idItem, boolean esBar) {
        interaccion.quitarItem(idItem, esBar);
    }
}