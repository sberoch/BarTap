package com.eriochrome.bartime.contracts;

import java.util.ArrayList;
import java.util.HashMap;

public interface DatosBarContract {

    interface Interaccion {
        void setNombre(String nombreBar);
        void setDesc(String desc);
        void setUbicacion(String ubicacion);
        void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal);
        void setHappyHour(HashMap<String, Integer> hhInicial, HashMap<String, Integer> hhFinal);
        void setMetodosPago(ArrayList<String> metodosDePago);
    }

    interface View {

    }
}