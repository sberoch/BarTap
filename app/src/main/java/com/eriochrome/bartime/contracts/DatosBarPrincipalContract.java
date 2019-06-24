package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.entidades.Bar;

public interface DatosBarPrincipalContract {

    interface Interaccion {
        void setNombre(String nombre);
        void setDescripcion(String descripcion);
        Bar getBar();
        void setUbicacion(String mock_text);
    }

    interface View {

    }
}