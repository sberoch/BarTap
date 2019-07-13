package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.entidades.Bar;

public interface DatosBarPrincipalContract {

    interface Interaccion {
        void setNombre(String nombre);
        void setDescripcion(String descripcion);
        Bar getBar();
        void setUbicacion(String direccion, double lat, double lng);
        void setBar(Bar bar);
        void cargarImagen(Bar bar);
        void subirFoto(Uri path);
    }

    interface View {
        void setTitleEditar();
        void setNombreBar(String nombre);
        void setDescripcion(String descripcion);
        void setUbicacion(String ubicacion);
        void onImageLoaded(String downloadUrl);
        void esModoEditar();
    }

    interface Listener {
        void onImageLoaded(String downloadUrl);
    }
}