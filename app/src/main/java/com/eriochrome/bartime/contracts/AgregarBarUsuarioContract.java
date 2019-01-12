package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.Bar;

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
