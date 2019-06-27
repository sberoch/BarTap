package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.HashMap;

public interface DatosBarHorariosContract {

    interface Interaccion {
        void setBar(Bar bar);
        Bar getBar();
        void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal);
        void setHappyHour(HashMap<String, Integer> happyhourInicial, HashMap<String, Integer> happyhourFinal);
    }

    interface View {
        void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal);
        void setHappyHour(HashMap<String, Integer> happyhourInicial, HashMap<String, Integer> happyhourFinal);
    }
}