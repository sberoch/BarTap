package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

public interface BarControlContract {

    interface Interaccion {
        void setupBar();
        String getNombreBar();
        Bar getBar();
        void subirFoto(Uri path);
        void checkearAvisos();
        void dejarDeCheckearAvisos();
    }

    interface View {
        void cargando();
        void finCargando();
        void hayAvisos();
        void noHayAvisos();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(Bar bar);
        void hayAvisos();
        void noHayAvisos();
    }
}