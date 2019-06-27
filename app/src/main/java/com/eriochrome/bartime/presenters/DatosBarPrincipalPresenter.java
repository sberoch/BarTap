package com.eriochrome.bartime.presenters;

import android.content.Intent;
import android.net.Uri;

import com.eriochrome.bartime.contracts.DatosBarPrincipalContract;
import com.eriochrome.bartime.modelos.DatosBarPrincipalInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;

public class DatosBarPrincipalPresenter implements DatosBarPrincipalContract.Listener {

    private DatosBarPrincipalContract.Interaccion interaccion;
    private DatosBarPrincipalContract.View view;

    public DatosBarPrincipalPresenter() {
        interaccion = new DatosBarPrincipalInteraccion(this);
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

    public void setUbicacion(String ubicacion) {
        interaccion.setUbicacion(ubicacion);
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        if (bar != null) {
            interaccion.setBar(bar);
            setInputs(bar);
            view.setTitleEditar();
            view.tieneFoto();
        }
    }

    private void setInputs(Bar bar) {
        view.setNombreBar(bar.getNombre());
        view.setDescripcion(bar.getDescripcion());
        view.setUbicacion(bar.getUbicacion());
        interaccion.cargarImagen(bar);
    }

    public void subirFoto(Uri path) throws RuntimeException {
        interaccion.subirFoto(path);
    }

    @Override
    public void onImageLoaded(String downloadUrl) {
        view.onImageLoaded(downloadUrl);
    }
}