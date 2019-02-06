package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

public interface NuevoJuegoContract {

    interface Interaccion {
        void setBar(Bar bar);
        Bar getBar();
    }

    interface View {

    }
}