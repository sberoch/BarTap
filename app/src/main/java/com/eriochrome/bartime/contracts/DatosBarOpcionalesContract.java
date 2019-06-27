package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public interface DatosBarOpcionalesContract {

    interface Interaccion {
        void setBar(Bar bar);
        void setMetodosDePago(ArrayList<String> metodosDePago);
        void subirDatos();
    }

    interface View {
        void terminar();
        void setMetodosDePago(ArrayList<String> metodosDePago);
    }

    interface Listener {
        void listo();
    }
}