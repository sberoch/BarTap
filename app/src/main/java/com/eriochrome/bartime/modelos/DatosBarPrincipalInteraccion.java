package com.eriochrome.bartime.modelos;

import android.net.Uri;

import com.eriochrome.bartime.contracts.DatosBarPrincipalContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DatosBarPrincipalInteraccion implements DatosBarPrincipalContract.Interaccion {

    private Bar bar;
    private StorageReference storageReference;
    private DatosBarPrincipalContract.Listener listener;

    public DatosBarPrincipalInteraccion(DatosBarPrincipalContract.Listener listener) {
        this.listener = listener;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void setNombre(String nombre) {
        if (bar != null) {
            bar.setNombre(nombre);
        } else {
            bar = new Bar(nombre);
        }
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

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void cargarImagen(Bar bar) {
        String path = bar.getNombre() + ".jpg";
        storageReference.child("imagenes").child(path).getDownloadUrl().addOnSuccessListener(uri -> {
            listener.onImageLoaded(uri.toString());
        });
    }

    @Override
    public void subirFoto(Uri path) throws RuntimeException {
        String nombreBar = bar.getNombre().replaceAll(" ", "_");
        String caminoEnStorage = nombreBar + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(caminoEnStorage);
        UploadTask uploadTask = imagenRef.putFile(path);
        uploadTask.addOnFailureListener(e -> {
            throw new RuntimeException();
        });
    }

}