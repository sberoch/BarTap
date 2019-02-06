package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.PaginaJuegoContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.PaginaJuegoInteraccion;

import java.util.ArrayList;

public class PaginaJuegoPresenter implements PaginaJuegoContract.Listener{

    private PaginaJuegoContract.Interaccion interaccion;
    private PaginaJuegoContract.View view;

    public PaginaJuegoPresenter() {
        interaccion = new PaginaJuegoInteraccion(this);
    }

    public void bind(PaginaJuegoContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerJuego(Intent intent) {
        Juego juego = (Juego) intent.getSerializableExtra("juego");
        interaccion.setJuego(juego);
    }

    public String getTipoDeJuego() {
        return interaccion.getTipoDeJuego();
    }

    public String getResumenJuego() {
        return interaccion.getResumenJuego();
    }

    public void setupAdapter() {
        view.cargando();
        interaccion.obtenerJuegos();
    }

    @Override
    public void onComplete(ArrayList<String> participantes) {
        view.finCargando(participantes);
    }

    public void onClickParticipante() {
        if (interaccion.esUnJuegoValidable()) {
            view.abrirDialogValidarGanador();
        }
    }

    public void declararGanador(String ganador) {
        interaccion.declararGanador(ganador);
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }
}