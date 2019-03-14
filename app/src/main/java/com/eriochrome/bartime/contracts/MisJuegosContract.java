package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Juego;

import java.util.ArrayList;

public interface MisJuegosContract {

    interface Interaccion {
        void mostrarJuegos();
        void dejarDeParticipar(Juego juego);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> juegos);
    }

    interface Listener {
        void listo(ArrayList<Juego> juegos);
    }
}