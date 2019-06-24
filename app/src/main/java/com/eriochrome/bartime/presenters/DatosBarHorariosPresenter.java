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
        interaccion.setBar((Bar) intent.getSerializableExtra("bar"));
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
}