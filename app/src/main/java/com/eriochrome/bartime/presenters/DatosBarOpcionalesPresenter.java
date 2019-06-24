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
        interaccion.setBar((Bar)intent.getSerializableExtra("bar"));
    }

    public void setMetodosDePago(ArrayList<String> metodosDePago) {
        interaccion.setMetodosDePago(metodosDePago);
    }

    public void subirBar() {
        interaccion.subirDatos();
        interaccion.subirImagenPrincipal();
    }

    @Override
    public void listo() {
        view.terminar();
    }
}