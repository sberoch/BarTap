package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Juego;

import java.util.ArrayList;

public interface JuegosGeneralContract {

    interface Interaccion {
        void setBar(Bar bar);
        void mostrarJuegos();
        Bar getBar();
        boolean esParticipable(Juego juego);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> listaJuegos);
    }

    interface Listener {
        void listo(ArrayList<Juego> listaJuegos);
    }
}