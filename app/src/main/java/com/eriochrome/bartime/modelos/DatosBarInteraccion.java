package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.DatosBarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;
import java.util.HashMap;

public class DatosBarInteraccion implements DatosBarContract.Interaccion {

    private Bar bar;

    public DatosBarInteraccion() {
        bar = new Bar("");
    }

    @Override
    public void setNombre(String nombreBar) {
        bar.setNombre(nombreBar);
    }

    @Override
    public void setDesc(String desc) {
        bar.setDescripcion(desc);
    }

    @Override
    public void setUbicacion(String ubicacion) {
        bar.setUbicacion(ubicacion);
    }

    @Override
    public void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        bar.agregarHorarios(horariosInicial, horariosFinal);
    }

    @Override
    public void setHappyHour(HashMap<String, Integer> hhInicial, HashMap<String, Integer> hhFinal) {
        bar.agregarHappyhourHorarios(hhInicial, hhFinal);
    }

    @Override
    public void setMetodosPago(ArrayList<String> metodosDePago) {
        bar.agregarMetodosDePago(metodosDePago);
    }
}