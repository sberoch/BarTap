package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.JuegosDelBarContract;
import com.eriochrome.bartime.modelos.JuegosDelBarInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;

import java.util.ArrayList;

public class JuegosDelBarPresenter implements JuegosDelBarContract.Listener {

    private JuegosDelBarContract.Interaccion interaccion;
    private JuegosDelBarContract.View view;

    public JuegosDelBarPresenter() {
        interaccion = new JuegosDelBarInteraccion(this);
    }

    public void bind(JuegosDelBarContract.View view) {
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
    public void onJuegosCargados(ArrayList<Juego> juegos) {
        view.finCargando(juegos);
    }

    @Override
    public void yaSeParticipo() {
        view.yaSeParticipo();
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
    public void ingresarATrivia(Trivia trivia) {
        view.ingresarATrivia(trivia);
    }

    public void obtenerBar(Intent intent) {
        interaccion.setBar((Bar) intent.getSerializableExtra("bar"));
    }

    public boolean estaConectado() {
        return interaccion.estaConectado();
    }

    public void intentarParticiparDeJuego(Juego juego) {
        interaccion.intentarParticiparDeJuego(juego);
    }
}