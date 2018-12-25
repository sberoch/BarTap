package com.eriochrome.bartime.presenters;

import android.app.AlertDialog;

import com.eriochrome.bartime.contracts.BaresFragmentContract;
import com.eriochrome.bartime.modelos.BaresFragmentInteraccion;
import com.eriochrome.bartime.modelos.Filtro;


public class BaresFragmentPresenter implements BaresFragmentContract.CompleteListener {

    private BaresFragmentContract.Interaccion interaccion;
    BaresFragmentContract.View view;

    public BaresFragmentPresenter() {
        this.interaccion = new BaresFragmentInteraccion(this);
    }

    public void bind(BaresFragmentContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void mostrarPrimerOrdenBares() {
        view.cargando();
        interaccion.buscarConPalabra("");
    }

    public void buscarConPalabra(String s) {
        view.cargando();
        interaccion.buscarConPalabra(s);
    }

    public void mostrarConFiltros(AlertDialog dialog) {
        view.cargando();
        Filtro filtro = new Filtro(view.hayOfertas(dialog));
        filtro.ordenarSegun(view.getOrdenamiento(dialog));
        interaccion.mostrarConFiltros(filtro);
    }

    @Override
    public void onSuccess() {
        view.finCargando(interaccion.obtenerLista());
    }


}


