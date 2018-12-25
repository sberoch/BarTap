package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.PaginaBarInteraccion;

public class PaginaBarPresenter {

    private PaginaBarContract.Interaccion interaccion;
    private PaginaBarContract.View view;

    public PaginaBarPresenter() {
        interaccion = new PaginaBarInteraccion();
    }

    public void bind(PaginaBarContract.View view) {
        this.view = view;
    }

    public void unbind(){
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public String getNombreDeBar() {
        return interaccion.getNombreDeBar();
    }

    public void calificarBar() {
        int calificacion = view.getCalificacion();
        interaccion.actualizarEstrellas(calificacion);
    }
}
