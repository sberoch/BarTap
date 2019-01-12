package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

import java.util.ArrayList;
import java.util.HashMap;

public interface DatosBarOwnerContract {

    interface Interaccion {
        void crearBar(String textNombreBar);
        Bar getBar();
        void subirBar();
        void subirFoto();
        void agregarFoto(Uri path);
        void editarBar();
        void setUbicacion(String direccion, double lat, double lng);
        String getDireccion();
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
        void setNombreBar(String nombre);
        void setHorariosIniciales(HashMap<String, Integer> horariosInicial);
        void setHorariosFinales(HashMap<String, Integer> horariosFinal);
        void setHappyHourInicial(HashMap<String, Integer> happyHourInicial);
        void setHappyHourFinal(HashMap<String, Integer> happyHourFinal);
        void setMetodosDePago(ArrayList<String> metodosDePago);
        void yaTieneImagen();
        void setTitleEditar();
        void setUbicacion(String ubicacion);
        String getDireccion();
        double getLat();
        double getLng();

    }

    interface CompleteListener {
        void onStart();
        void onSuccess();
    }
}