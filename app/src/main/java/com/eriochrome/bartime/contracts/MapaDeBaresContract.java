package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

public interface MapaDeBaresContract {

    interface Interaccion {
        void getPosicionesDeBares();
    }

    interface View {
        void marcarBar(Bar bar);
        void listo();
    }

    interface Listener {
        void marcarBar(Bar bar);
        void listo();
    }
}