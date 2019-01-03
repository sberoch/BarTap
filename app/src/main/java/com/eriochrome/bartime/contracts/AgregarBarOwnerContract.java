package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String, Integer> getHorariosInicial();
        HashMap<String, Integer> getHorariosFinal();
        boolean tieneHappyHour();
        HashMap<String, Integer> getHappyhourInicial();
        HashMap<String, Integer> getHappyhourFinal();
        ArrayList<String> obtenerMetodosDePago();
        void subiendo();
        void finSubiendo();
        boolean hayImagen();
        void noHayImagenError();
        void mostrarError();
    }

    interface CompleteListener {
        void onStart();
        void onSuccess();
    }
}