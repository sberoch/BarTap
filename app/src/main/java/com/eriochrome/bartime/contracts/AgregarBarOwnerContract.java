package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

import java.util.ArrayList;

public interface AgregarBarOwnerContract {

    interface Interaccion {
        void crearBar(String textNombreBar);
        Bar getBar();
        void subirBar();
        void subirFoto();
        void agregarFoto(Uri path);
    }

    interface View {
        String getTextNombreBar();
        int getHorarioInicial();
        int getHorarioFinal();
        boolean tieneHappyHour();
        int getHappyhourInicial();
        int getHappyhourFinal();
        ArrayList<String> obtenerMetodosDePago();
        void subiendo();
        void finSubiendo();
        boolean hayImagen();
        void noHayImagenError();


    }

    interface CompleteListener {
        void onStart();
        void onSuccess();
    }
}