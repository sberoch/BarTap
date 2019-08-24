package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.PaginaSorteoContract;
import com.eriochrome.bartime.modelos.PaginaSorteoInteraccion;
import com.eriochrome.bartime.modelos.entidades.Juego;

public class PaginaSorteoPresenter implements PaginaSorteoContract.Listener {

    private PaginaSorteoContract.Interaccion interaccion;
    private PaginaSorteoContract.View view;

    public PaginaSorteoPresenter() {
        interaccion = new PaginaSorteoInteraccion(this);
    }

    public void bind(PaginaSorteoContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerJuego(Intent intent) {
        Juego juego = (Juego)intent.getSerializableExtra("juego");
        interaccion.setSorteo(juego);
    }

    public void sortear() {
        view.cargando();
        interaccion.sortear();
    }

    @Override
    public void finSorteo(String participanteGanador) {
        view.finSorteo(participanteGanador);
        interaccion.borrarSorteo();
    }

    @Override
    public void setCantParticipantes(long childrenCount) {
        view.setCantParticipantes((int) childrenCount);
        view.finCargando();
    }

    public void cargarDatos() {
        view.cargando();
        view.setResumenJuego(interaccion.getResumenJuego());
        interaccion.cargarCantidadDeParticipantes();
    }
}