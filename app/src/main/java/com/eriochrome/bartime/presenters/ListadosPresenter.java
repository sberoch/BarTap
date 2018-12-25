package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.modelos.ListadosInteraccion;

public class ListadosPresenter {

    private ListadosContract.Interaccion interaccion;
    private ListadosContract.View view;

    public ListadosPresenter() {
        this.interaccion = new ListadosInteraccion();
    }

    public void bind(ListadosContract.View view) {
        this.view = view;
    }

    public void unbind() {
        this.view = null;
    }

    public boolean estaConectado() {
        return interaccion.estaConectado();
    }

    public void subirUsuarioADatabase() {
        interaccion.subirUsuarioADatabase();
    }

    public String getNombreUsuario() {
        return interaccion.getNombreUsuario();
    }
}
