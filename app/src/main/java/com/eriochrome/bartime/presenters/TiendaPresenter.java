package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.TiendaContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.eriochrome.bartime.modelos.TiendaInteraccion;

import java.util.ArrayList;

public class TiendaPresenter implements TiendaContract.Listener {

    private TiendaContract.Interaccion interaccion;
    private TiendaContract.View view;

    public TiendaPresenter() {
        interaccion = new TiendaInteraccion(this);
    }

    public void bind(TiendaContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public void setupTienda() {
        view.cargando();
        interaccion.setupTienda();
    }

    @Override
    public void listo(ArrayList<ItemTienda> items, Integer misPuntos) {
        view.finCargando(items, misPuntos);
        interaccion.guardarPuntos(misPuntos);
    }

    public int getPuntos() {
        return interaccion.getPuntos();
    }

    public void comprarItem(ItemTienda itemTienda) {
        interaccion.comprarItem(itemTienda);
    }

    public void onPause() {
        interaccion.dejarDeEscucharCambios();
    }
}