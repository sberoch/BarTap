package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Juego;

import java.util.ArrayList;

public interface JuegosFragmentContract {

    interface Interaccion {
        void mostrarJuegosConPalabra(String s);
        ArrayList<Juego> obtenerJuegos();
        void participarDeJuego(Juego juego);
        boolean estaConectado();

    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> juegos);
        void successParticipando();
    }

    interface Listener {
        void listo();
        void successParticipando();
    }
}