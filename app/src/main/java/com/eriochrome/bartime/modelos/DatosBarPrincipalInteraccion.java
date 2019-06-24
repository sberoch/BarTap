package com.eriochrome.bartime.modelos;

import android.net.Uri;

import com.eriochrome.bartime.contracts.DatosBarPrincipalContract;
import com.eriochrome.bartime.modelos.entidades.Bar;

public class DatosBarPrincipalInteraccion implements DatosBarPrincipalContract.Interaccion {

    private Bar bar;

    @Override
    public void setNombre(String nombre) {
        bar = new Bar(nombre);
    }

    @Override
    public void setDescripcion(String descripcion) {
        bar.setDescripcion(descripcion);
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void setUbicacion(String mock_text) {
        bar.setUbicacion(mock_text);
    }

}