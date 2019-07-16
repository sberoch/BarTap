package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;

public interface DatosBarOpcionalesContract {

    interface Interaccion {
        void setBar(Bar bar);
        void setMetodosDePago(ArrayList<String> metodosDePago);
        void subirDatos();
        void setTelefono(String telefono);
    }

    interface View {
        void terminar();
        void setMetodosDePago(ArrayList<String> metodosDePago);
        void setTelefono(String telefono);
    }

    interface Listener {
        void listo();
    }
}