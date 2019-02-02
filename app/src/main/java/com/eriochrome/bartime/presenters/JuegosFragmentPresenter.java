package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.JuegosFragmentContract;
import com.eriochrome.bartime.modelos.Juego;
import com.eriochrome.bartime.modelos.JuegosFragmentInteraccion;

public class JuegosFragmentPresenter implements JuegosFragmentContract.Listener{

    private JuegosFragmentContract.Interaccion interaccion;
    private JuegosFragmentContract.View view;

    public JuegosFragmentPresenter() {
        interaccion = new JuegosFragmentInteraccion(this);
    }

    public void bind(JuegosFragmentContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void mostrarJuegos() {
        view.cargando();
        interaccion.mostrarJuegosConPalabra("");
    }

    public void buscar(String s) {
        view.cargando();
        interaccion.mostrarJuegosConPalabra(s);
    }

    @Override
    public void listo() {
        view.finCargando(interaccion.obtenerJuegos());
    }

    @Override
    public void successParticipando() {
        view.successParticipando();
    }

    public void participarDeJuego(Juego juego) {
        interaccion.participarDeJuego(juego);
    }

    public boolean estaConectado() {
        return interaccion.estaConectado();
    }

}