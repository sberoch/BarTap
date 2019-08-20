package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public interface DatosBarReclamarContract {

    interface Interaccion {

        void reclamarBar(Bar bar);
        void mostrarBaresParaReclamar();
        void setBar(Bar bar);
        Bar getBar();
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Bar> nuevosBares);
    }

    interface Listener {
        void listo(ArrayList<Bar> nuevosBares);
    }
}