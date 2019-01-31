package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

public interface BarControlContract {

    interface Interaccion {
        void setupBar();
        String getNombreBar();
        Bar getBar();
        void subirFoto(Uri path);
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