package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.CrearSorteoContract;
import com.eriochrome.bartime.modelos.CrearSorteoInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

public class CrearSorteoPresenter implements CrearSorteoContract.Listener {

    private CrearSorteoContract.Interaccion interaccion;
    private CrearSorteoContract.View view;

    public CrearSorteoPresenter() {
        interaccion = new CrearSorteoInteraccion(this);
    }

    public void bind(CrearSorteoContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void enviarSorteo() {
        String fechaFin = view.getFechaFin();
        String puntos = view.getPuntos();
        interaccion.enviarSorteo(fechaFin, puntos);
    }

    @Override
    public void enviado() {
        view.enviado();
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }
}