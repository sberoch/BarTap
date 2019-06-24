package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public interface DatosBarOpcionalesContract {

    interface Interaccion {
        void setBar(Bar bar);
        void setMetodosDePago(ArrayList<String> metodosDePago);
        void subirDatos();
        void subirImagenPrincipal();
    }

    interface View {
        void terminar();
    }

    interface Listener {
        void listo();
    }
}