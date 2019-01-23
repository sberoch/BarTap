package com.eriochrome.bartime.presenters;

import android.app.AlertDialog;
import android.location.Location;

import com.eriochrome.bartime.contracts.BaresFragmentContract;
import com.eriochrome.bartime.modelos.BaresFragmentInteraccion;
import com.eriochrome.bartime.modelos.Filtro;


public class BaresFragmentPresenter implements BaresFragmentContract.CompleteListener {

    private BaresFragmentContract.Interaccion interaccion;
    BaresFragmentContract.View view;

    Filtro filtro;

    public BaresFragmentPresenter() {
        this.interaccion = new BaresFragmentInteraccion(this);
        filtro = new Filtro();
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
        filtro.aplicarAbierto(view.filtroAbierto(dialog));
        filtro.aplicarHappyHour(view.filtroHappyHour(dialog));
        filtro.aplicarOfertas(view.filtroOfertas(dialog));
        filtro.aplicarMetodosDePago(view.filtroEfectivo(dialog),
                                    view.filtroCredito(dialog),
                                    view.filtroDebito(dialog));
        filtro.ordenarSegun(view.getOrdenamiento(dialog));
        interaccion.mostrarConFiltros(filtro);
    }

    @Override
    public void onSuccess() {
        view.finCargando(interaccion.obtenerLista());
    }

    public void setUltimaUbicacion(Location ultimaUbicacion) {
        interaccion.setUltimaUbicacion(ultimaUbicacion);
    }
}


