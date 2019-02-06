package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Juego;

import java.util.ArrayList;

public interface PaginaJuegoContract {

    interface Interaccion {
        void setJuego(Juego juego);
        String getTipoDeJuego();
        String getResumenJuego();
        void obtenerJuegos();
        boolean esUnJuegoValidable();
        void declararGanador(String ganador);
        void setBar(Bar bar);
    }

    interface View {

        void cargando();
        void finCargando(ArrayList<String> participantes);
        void abrirDialogValidarGanador();
    }

    interface Listener {
       void onComplete(ArrayList<String> participantes);
    }
}