package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.DatosBarOpcionalesContract;
import com.eriochrome.bartime.modelos.DatosBarOpcionalesInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public class DatosBarOpcionalesPresenter implements DatosBarOpcionalesContract.Listener{

    private DatosBarOpcionalesContract.Interaccion interaccion;
    private DatosBarOpcionalesContract.View view;

    public DatosBarOpcionalesPresenter() {
        interaccion = new DatosBarOpcionalesInteraccion(this);
    }

    public void bind(DatosBarOpcionalesContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar)intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
        view.setMetodosDePago(bar.getMetodosDePago());
        view.setTelefono(bar.getTelefono());

    }

    public void setMetodosDePago(ArrayList<String> metodosDePago) {
        interaccion.setMetodosDePago(metodosDePago);
    }

    public void subirBar() {
        interaccion.subirDatos();
    }

    @Override
    public void listo() {
        view.terminar();
    }

    public void setTelefono(String telefono) {
        interaccion.setTelefono(telefono);
    }
}