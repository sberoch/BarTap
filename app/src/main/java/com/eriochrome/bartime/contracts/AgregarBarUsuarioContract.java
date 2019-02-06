package com.eriochrome.bartime.contracts;

import android.net.Uri;

public interface AgregarBarUsuarioContract {

    interface Interaccion {
        void crearBar(String nombreBar);
        void agregarUbicacion(String direccion, double lat, double lng);
        void agregarImagen(Uri path);
        void subirBar();

    }

    interface View {
        boolean hayImagenSeleccionada();
        void startConfirmacion();
        boolean hayUbicacionSeleccionada();
        boolean hayNombreValido();

    }
}
