package com.eriochrome.bartime.presenters;

import android.net.Uri;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.modelos.ListadosInteraccion;

public class ListadosPresenter implements ListadosContract.CompleteListener{

    private ListadosContract.Interaccion interaccion;
    private ListadosContract.View view;

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

    @Override
    public void hayAvisos() {
        view.hayAvisos();
    }

    @Override
    public void noHayAvisos() {
        view.noHayAvisos();
    }

    public void checkearAvisos() {
        interaccion.checkearAvisos();
    }

    public void dejarDeCheckearAvisos() {
        interaccion.dejarDeCheckearAvisos();
    }

}
