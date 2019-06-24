package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.DatosBarHorariosContract;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.HashMap;

public class DatosBarHorariosInteraccion implements DatosBarHorariosContract.Interaccion {

    private Bar bar;

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        bar.agregarHorarios(horariosInicial, horariosFinal);
    }

    @Override
    public void setHappyHour(HashMap<String, Integer> happyhourInicial, HashMap<String, Integer> happyhourFinal) {
        bar.agregarHappyhourHorarios(happyhourInicial, happyhourFinal);
    }
}