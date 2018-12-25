package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

public interface AgregarBarUsuarioContract {

    interface Interaccion {
        void agregarBar(Bar bar, Uri path);
    }

    interface View {
        boolean hayImagenSeleccionada();
        void startConfirmacion();
    }
}
