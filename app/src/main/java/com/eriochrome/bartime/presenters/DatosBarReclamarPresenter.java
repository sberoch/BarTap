package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.DatosBarReclamarContract;
import com.eriochrome.bartime.modelos.DatosBarReclamarInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public class DatosBarReclamarPresenter implements DatosBarReclamarContract.Listener {

    private DatosBarReclamarContract.Interaccion interaccion;
    private DatosBarReclamarContract.View view;

    public DatosBarReclamarPresenter() {
        interaccion = new DatosBarReclamarInteraccion(this);
    }

    public void bind(DatosBarReclamarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void reclamarBar(Bar bar) {
        interaccion.reclamarBar(bar);
    }

    public void mostrarBaresParaReclamar() {
        view.cargando();
        interaccion.mostrarBaresParaReclamar();
    }

    @Override
    public void listo(ArrayList<Bar> nuevosBares) {
        view.finCargando(nuevosBares);
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar)intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }
}