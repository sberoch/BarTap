package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.MapaDeBaresContract;
import com.eriochrome.bartime.modelos.MapaDeBaresInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

public class MapaDeBaresPresenter implements MapaDeBaresContract.Listener {

    private MapaDeBaresContract.Interaccion interaccion;
    private MapaDeBaresContract.View view;

    public MapaDeBaresPresenter() {
        interaccion = new MapaDeBaresInteraccion(this);
    }

    public void bind(MapaDeBaresContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void getPosicionesDeBares() {
        interaccion.getPosicionesDeBares();
    }

    @Override
    public void marcarBar(Bar bar) {
        view.marcarBar(bar);
    }

    @Override
    public void listo() {
        view.listo();
    }
}