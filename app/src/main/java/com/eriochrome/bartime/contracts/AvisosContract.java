package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Aviso;

import java.util.ArrayList;

public interface AvisosContract {

    interface Interaccion {
        void cargarAvisos(boolean esBar);
        void quitarItem(String idItem, boolean esBar);
        void quitarConNombreBar(String idItem, String nombreBar);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Aviso> avisos);
    }

    interface Listener {
        void listo(ArrayList<Aviso> avisos);
        void quitarItemBar(String idItem, String nombreBar);
    }
}