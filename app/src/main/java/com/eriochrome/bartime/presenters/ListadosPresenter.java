package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.modelos.ListadosInteraccion;

public class ListadosPresenter implements ListadosContract.CompleteListener{

    private ListadosContract.Interaccion interaccion;
    private ListadosContract.View view;
    private boolean esNuevoUsuario;

    public ListadosPresenter() {
        this.interaccion = new ListadosInteraccion(this);
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

    public boolean esNuevoUsuario() {
        interaccion.checkearNuevo();
        return esNuevoUsuario;
    }

    @Override
    public void checkearNuevo(boolean esNuevoUsuario) {
        this.esNuevoUsuario = esNuevoUsuario;
    }

    public void conectar() {
        interaccion.conectar();
    }
}
