package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;

public interface NuevoJuegoContract {

    interface Interaccion {
        void setBar(Bar bar);
        Bar getBar();
    }

    interface View {

    }
}