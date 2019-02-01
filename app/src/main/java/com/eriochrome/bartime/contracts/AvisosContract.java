package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Aviso;

import java.util.ArrayList;

public interface AvisosContract {

    interface Interaccion {
        void cargarAvisos();
        void quitarItem(String idItem);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Aviso> avisos);
    }

    interface Listener {
        void listo(ArrayList<Aviso> avisos);
    }
}