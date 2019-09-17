package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;

import java.util.ArrayList;

public interface JuegosFragmentContract {

    interface Interaccion {
        void mostrarJuegosConPalabra(String s);
        ArrayList<Juego> obtenerJuegos();
        void participarDeJuego(Juego juego);
        boolean estaConectado();
        void intentarParticiparDeJuego(Juego juego);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> juegos);
        void successParticipando(Juego juego);
        void yaSeParticipo();
        void ingresarATrivia(Trivia trivia);
    }

    interface Listener {
        void listo();
        void successParticipando(Juego juego);
        void yaSeParticipo();
        void participarDeJuego(Juego juego);
        void ingresarATrivia(Trivia trivia);
    }
}