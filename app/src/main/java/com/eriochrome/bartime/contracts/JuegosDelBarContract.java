package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;

import java.util.ArrayList;

public interface JuegosDelBarContract {

    interface Interaccion {

        void setBar(Bar bar);
        void mostrarJuegos();
        boolean estaConectado();
        void intentarParticiparDeJuego(Juego juego);
        void participarDeJuego(Juego juego);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> juegos);
        void yaSeParticipo();
        void successParticipando();
        void ingresarATrivia(Trivia trivia);
    }

    interface Listener {
        void onJuegosCargados(ArrayList<Juego> juegos);
        void yaSeParticipo();
        void participarDeJuego(Juego juego);
        void successParticipando();
        void ingresarATrivia(Trivia trivia);
    }
}