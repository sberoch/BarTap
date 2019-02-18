package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.JuegosFragmentContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.JuegosFragmentInteraccion;
import com.eriochrome.bartime.modelos.entidades.Trivia;

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

    public void intentarParticiparDeJuego(Juego juego) {
        interaccion.intentarParticiparDeJuego(juego);
    }

    @Override
    public void participarDeJuego(Juego juego) {
        interaccion.participarDeJuego(juego);
    }

    @Override
    public void successParticipando() {
        view.successParticipando();
    }

    @Override
    public void yaSeParticipo() {
        view.yaSeParticipo();
    }

    @Override
    public void ingresarATrivia(Trivia trivia) {
        view.ingresarATrivia(trivia);
    }

    public boolean estaConectado() {
        return interaccion.estaConectado();
    }

}