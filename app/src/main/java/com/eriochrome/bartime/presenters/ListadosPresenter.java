package com.eriochrome.bartime.presenters;

import android.net.Uri;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.modelos.ListadosInteraccion;
import com.eriochrome.bartime.modelos.entidades.Juego;

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

    @Override
    public void abrirSorteo(Juego juego) {
         view.abrirSorteo(juego);
    }

    public void checkearAvisos() {
        interaccion.checkearAvisos();
    }

    public void dejarDeCheckearAvisos() {
        interaccion.dejarDeCheckearAvisos();
    }

    public void anotarReferrer(String referrerUid, String gameID) {
        interaccion.anotarReferrer(referrerUid, gameID);
    }

    public void obtenerSorteoConId(String gameID) {
        interaccion.obtenerSorteoConId(gameID);
    }
}
