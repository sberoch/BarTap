package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.CrearDesafioContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.CrearDesafioInteraccion;

public class CrearDesafioPresenter implements CrearDesafioContract.Callback{

    private CrearDesafioContract.Interaccion interaccion;
    private CrearDesafioContract.View view;

    public CrearDesafioPresenter() {
        interaccion = new CrearDesafioInteraccion(this);
    }

    public void bind(CrearDesafioContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public void enviarDesafio() {
        String desafio = view.getDesafioText();
        String dificultad = view.getDificultad();
        boolean unicoGanador = view.esDeUnicoGanador();
        interaccion.enviarDesafio(desafio, dificultad, unicoGanador);
    }

    @Override
    public void enviado() {
        view.enviado();
    }
}