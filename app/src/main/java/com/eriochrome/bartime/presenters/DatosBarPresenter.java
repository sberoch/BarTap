package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.DatosBarContract;
import com.eriochrome.bartime.modelos.DatosBarInteraccion;

import java.util.ArrayList;
import java.util.HashMap;

public class DatosBarPresenter {

    private DatosBarContract.Interaccion interaccion;
    private DatosBarContract.View view;

    public DatosBarPresenter() {
        interaccion = new DatosBarInteraccion();
    }

    public void bind(DatosBarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void setNombre(String nombreBar) {
        interaccion.setNombre(nombreBar);
    }

    public void setDesc(String desc) {
        interaccion.setDesc(desc);
    }

    public void setUbicacion(String ubicacion) {
        interaccion.setUbicacion(ubicacion);
    }

    public void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        interaccion.setHorarios(horariosInicial, horariosFinal);
    }

    public void setHappyHour(HashMap<String, Integer> hhInicial, HashMap<String, Integer> hhFinal) {
        interaccion.setHappyHour(hhInicial, hhFinal);
    }

    public void setMetodosPago(ArrayList<String> metodosDePago) {
        interaccion.setMetodosPago(metodosDePago);
    }
}