package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.MisJuegosContract;
import com.eriochrome.bartime.modelos.MisJuegosInteraccion;
import com.eriochrome.bartime.modelos.entidades.Juego;

import java.util.ArrayList;

public class MisJuegosPresenter implements MisJuegosContract.Listener{

    private MisJuegosContract.Interaccion interaccion;
    private MisJuegosContract.View view;

    public MisJuegosPresenter() {
        interaccion = new MisJuegosInteraccion(this);
    }

    public void bind(MisJuegosContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void mostrarJuegos() {
        view.cargando();
        interaccion.mostrarJuegos();
    }

    @Override
    public void listo(ArrayList<Juego> juegos) {
        view.finCargando(juegos);
    }

    public void dejarDeParticipar(Juego juego) {
        interaccion.dejarDeParticipar(juego);
    }
}