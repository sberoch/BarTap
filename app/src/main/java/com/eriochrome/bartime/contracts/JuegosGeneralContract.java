package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.Juego;

import java.util.ArrayList;

public interface JuegosGeneralContract {

    interface Interaccion {
        void setBar(Bar bar);
        void mostrarJuegos();
        Bar getBar();
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> listaJuegos);
    }

    interface Listener {
        void listo(ArrayList<Juego> listaJuegos);
    }
}