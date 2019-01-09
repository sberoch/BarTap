package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;

public interface BarControlContract {

    interface Interaccion {
        void setupBar();
        String getNombreBar();
        Bar getBar();
        void crearOferta(String oferta, String fechafinal);
    }

    interface View {
        void cargando();
        void finCargando();
        void finCrearOferta();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(Bar bar);
        void finCrearOferta();
    }
}