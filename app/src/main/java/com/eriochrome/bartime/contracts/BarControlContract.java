package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;

public interface BarControlContract {

    interface Interaccion {
        void setupBar();
        String getNombreBar();
        Bar getBar();
    }

    interface View {
        void cargando();
        void finCargando();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(Bar bar);
    }
}