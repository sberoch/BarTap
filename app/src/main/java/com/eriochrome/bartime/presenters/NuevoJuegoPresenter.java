package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.NuevoJuegoContract;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.NuevoJuegoInteraccion;

public class NuevoJuegoPresenter {

    private NuevoJuegoContract.Interaccion interaccion;
    private NuevoJuegoContract.View view;

    public NuevoJuegoPresenter() {
        interaccion = new NuevoJuegoInteraccion();
    }

    public void bind(NuevoJuegoContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }
}