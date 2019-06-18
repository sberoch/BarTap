package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.eriochrome.bartime.modelos.entidades.Bar;

public interface BarControlContract {

    interface Interaccion {
        void setupBar();
        String getNombreBar();
        Bar getBar();
        void subirFoto(Uri path);
        void checkearAvisos();
        void dejarDeCheckearAvisos();
        void cargarImagenes();
    }

    interface View {
        void cargando();
        void finCargando();
        void hayAvisos();
        void noHayAvisos();
        void onImageLoaded(String path);
    }

    interface CompleteListener {
        void onStart();
        void onComplete(Bar bar);
        void hayAvisos();
        void noHayAvisos();
        void onImageLoaded(String path);
    }
}