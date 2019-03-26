package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.TiendaBarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.eriochrome.bartime.modelos.TiendaBarInteraccion;

import java.util.ArrayList;

public class TiendaBarPresenter implements TiendaBarContract.Listener{

    private TiendaBarContract.Interaccion interaccion;
    private TiendaBarContract.View view;

    public TiendaBarPresenter() {
        interaccion = new TiendaBarInteraccion(this);
    }

    public void bind(TiendaBarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void mostrarItemsTienda() {
        view.cargando();
        interaccion.mostrarItemsTienda();
    }

    @Override
    public void listo(ArrayList<ItemTienda> items) {
        view.finCargando(items);
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public void crearItem(ItemTienda itemTienda) {
        view.cargando();
        interaccion.crearItem(itemTienda);
    }

    public void eliminarItem(ItemTienda itemTienda) {
        interaccion.eliminarItem(itemTienda);
    }

    public void dejarDeMostrarItems() {
        interaccion.dejarDeMostrarItems();
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }
}