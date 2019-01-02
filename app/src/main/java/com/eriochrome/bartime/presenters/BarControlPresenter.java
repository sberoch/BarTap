package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.modelos.Bar;
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
        view.finCargando();
    }

    public String getNombreBar() {
        return interaccion.getNombreBar();
    }


    public void editarBar() {

    }

    public void crearOferta() {

    }

    public void crearDesafio() {

    }

    public String getNombreUsuario() {
        return interaccion.getNombreBar();
    }
}