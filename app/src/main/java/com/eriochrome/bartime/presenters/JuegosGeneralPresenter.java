package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.JuegosFragmentContract;
import com.eriochrome.bartime.contracts.JuegosGeneralContract;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.Juego;
import com.eriochrome.bartime.modelos.JuegosGeneralInteraccion;
import com.eriochrome.bartime.vistas.JuegosGeneralActivity;

import java.util.ArrayList;

public class JuegosGeneralPresenter implements JuegosGeneralContract.Listener {

    private JuegosGeneralContract.Interaccion interaccion;
    private JuegosGeneralContract.View view;

    public JuegosGeneralPresenter() {
        interaccion = new JuegosGeneralInteraccion(this);
    }

    public void bind(JuegosGeneralContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void mostrarJuegos() {
        view.cargando();
        interaccion.mostrarJuegos();
    }

    public void obtenerBar(Intent intent) {
        interaccion.setBar((Bar) intent.getSerializableExtra("bar"));
    }


    @Override
    public void listo(ArrayList<Juego> listaJuegos) {
        view.finCargando(listaJuegos);
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }

    public Intent enviarJuego(Intent i, Juego juego) {
        return i.putExtra("juego", juego);
    }
}