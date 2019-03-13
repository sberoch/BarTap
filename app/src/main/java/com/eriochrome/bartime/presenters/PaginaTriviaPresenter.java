package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.PaginaTriviaContract;
import com.eriochrome.bartime.modelos.PaginaTriviaInteraccion;
import com.eriochrome.bartime.modelos.entidades.Juego;

public class PaginaTriviaPresenter implements PaginaTriviaContract.Listener{

    private PaginaTriviaContract.Interaccion interaccion;
    private PaginaTriviaContract.View view;

    public PaginaTriviaPresenter() {
        interaccion = new PaginaTriviaInteraccion(this);
    }

    public void bind(PaginaTriviaContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerJuego(Intent intent) {
        Juego juego = (Juego)intent.getSerializableExtra("juego");
        interaccion.setTrivia(juego);
    }

    public void cargarDatosParticipantes() {
        view.cargando();
        interaccion.cargarDatosParticipantes();
    }

    @Override
    public void onComplete(int participantes, int ganadores) {
        view.setGanadores(ganadores);
        view.setParticipantes(participantes);
        view.finCargando();
    }

    public void cargarDatosJuego() {
        view.setTipoDeJuego(interaccion.getTipoDeJuego());
        view.setResumen(interaccion.getResumen());
    }
}