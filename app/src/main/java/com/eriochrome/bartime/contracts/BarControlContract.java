package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;

public interface BarControlContract {

    interface Interaccion {
        void setupBar();
        String getNombreBar();
        Bar getBar();
        void crearOfertaMock();
    }

    interface View {
        void cargando();
        void finCargando();
        void finCreandoOferta();
        void creandoOferta();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(Bar bar);
        void creandoOferta();
        void finCreandoOferta();
    }
}