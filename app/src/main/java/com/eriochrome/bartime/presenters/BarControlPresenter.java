package com.eriochrome.bartime.presenters;

import android.content.Intent;
import android.net.Uri;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.BarControlInteraccion;

public class BarControlPresenter implements BarControlContract.CompleteListener {

    private BarControlContract.Interaccion interaccion;
    private BarControlContract.View view;
    private boolean hayBarAsociado;

    public BarControlPresenter() {
        interaccion = new BarControlInteraccion(this);
        hayBarAsociado = false;
    }

    public void bind(BarControlContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void setupBar() {
        interaccion.setupBar();
    }

    public boolean hayBarAsociado() {
        return hayBarAsociado;
    }

    @Override
    public void onStart() {
        view.cargando();
    }

    @Override
    public void onComplete(Bar bar) {
        hayBarAsociado = (bar != null);
        checkearAvisos();
        view.finCargando();
    }

    @Override
    public void hayAvisos() {
        view.hayAvisos();
    }

    @Override
    public void noHayAvisos() {
        view.noHayAvisos();
    }

    public String getNombreBar() {
        return interaccion.getNombreBar();
    }


    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }


    public String getNombreUsuario() {
        return interaccion.getNombreBar();
    }


    public void subirFoto(Uri path) {
        interaccion.subirFoto(path);
    }

    public void checkearAvisos() {
        interaccion.checkearAvisos();
    }

    public void dejarDeCheckearAvisos() {
        interaccion.dejarDeCheckearAvisos();
    }
}