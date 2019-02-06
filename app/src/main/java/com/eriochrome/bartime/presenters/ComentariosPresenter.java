package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.ComentariosContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.ComentariosInteraccion;

public class ComentariosPresenter implements ComentariosContract.CompleteListener{

    private ComentariosContract.Interaccion interaccion;
    private ComentariosContract.View view;

    public ComentariosPresenter() {
        interaccion = new ComentariosInteraccion(this);
    }

    public void bind(ComentariosContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    @Override
    public void onStart() {
        view.cargando();
    }

    @Override
    public void onSuccess() {
        view.finCargando(interaccion.getListaComentarios());
    }

    public void mostrarComentarios() {
        interaccion.mostrarComentarios();
    }
}