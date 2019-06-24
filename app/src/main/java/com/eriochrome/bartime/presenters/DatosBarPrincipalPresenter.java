package com.eriochrome.bartime.presenters;

import android.content.Intent;
import android.net.Uri;

import com.eriochrome.bartime.contracts.DatosBarPrincipalContract;
import com.eriochrome.bartime.modelos.DatosBarPrincipalInteraccion;

public class DatosBarPrincipalPresenter {

    private DatosBarPrincipalContract.Interaccion interaccion;
    private DatosBarPrincipalContract.View view;

    public DatosBarPrincipalPresenter() {
        interaccion = new DatosBarPrincipalInteraccion();
    }

    public void bind(DatosBarPrincipalContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void setNombre(String nombre) {
        interaccion.setNombre(nombre);
    }

    public void setDescripcion(String descripcion) {
        interaccion.setDescripcion(descripcion);
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }

    public void setUbicacion(String mock_text) {
        interaccion.setUbicacion(mock_text);
    }
}