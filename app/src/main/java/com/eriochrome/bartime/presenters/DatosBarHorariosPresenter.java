package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.DatosBarHorariosContract;
import com.eriochrome.bartime.modelos.DatosBarHorariosInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.HashMap;

public class DatosBarHorariosPresenter {

    private DatosBarHorariosContract.Interaccion interaccion;
    private DatosBarHorariosContract.View view;

    public DatosBarHorariosPresenter() {
        interaccion = new DatosBarHorariosInteraccion();
    }

    public void bind(DatosBarHorariosContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
        if (estaAbierto(bar)) {
            setInputs(bar);
        }
    }

    private void setInputs(Bar bar) {
        view.setHorarios(bar.getHorariosInicial(), bar.getHorariosFinal());
        if (tieneHappyHour(bar)) {
            view.setHappyHour(bar.getHappyhourInicial(), bar.getHappyhourFinal());
        }
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }

    public void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        interaccion.setHorarios(horariosInicial, horariosFinal);
    }

    public void setHappyHour(HashMap<String, Integer> happyhourInicial, HashMap<String, Integer> happyhourFinal) {
        interaccion.setHappyHour(happyhourInicial, happyhourFinal);
    }

    private boolean estaAbierto(Bar bar) {
        boolean hayDiaConHorarioInicial = false;
        boolean hayDiaConHorarioFinal = false;
        for (Integer horarioInicial : bar.getHorariosInicial().values()) {
            if (horarioInicial != 0) hayDiaConHorarioInicial = true;
        }
        for (Integer horarioFinal : bar.getHorariosFinal().values()) {
            if (horarioFinal != 0) hayDiaConHorarioFinal = true;
        }
        return hayDiaConHorarioInicial && hayDiaConHorarioFinal;
    }

    private boolean tieneHappyHour(Bar bar) {
        boolean hayDiaConHHInicial = false;
        boolean hayDiaConHHFinal = false;
        for (Integer hhInicial : bar.getHappyhourInicial().values()) {
            if (hhInicial != 0) hayDiaConHHInicial = true;
        }
        for (Integer hhFinal : bar.getHappyhourFinal().values()) {
            if (hhFinal != 0) hayDiaConHHFinal = true;
        }
        return hayDiaConHHInicial && hayDiaConHHFinal;
    }
}