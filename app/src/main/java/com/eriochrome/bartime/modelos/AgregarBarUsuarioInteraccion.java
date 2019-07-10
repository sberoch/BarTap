package com.eriochrome.bartime.modelos;

import android.net.Uri;

import com.eriochrome.bartime.contracts.AgregarBarUsuarioContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AgregarBarUsuarioInteraccion implements AgregarBarUsuarioContract.Interaccion {

    private DatabaseReference baresRevisionRef;
    private StorageReference storageReference;
    private Bar bar;

   public AgregarBarUsuarioInteraccion() {
       baresRevisionRef = FirebaseDatabase.getInstance().getReference().child("baresEnRevision");
       storageReference = FirebaseStorage.getInstance().getReference();
   }

    @Override
    public void crearBar(String nombreBar) {
        bar = new Bar(nombreBar);
    }

    @Override
    public void agregarUbicacion(String direccion, double lat, double lng) {
        bar.setUbicacion(direccion);
        bar.setLatLng(lat, lng);
    }

    @Override
    public void agregarImagen(Uri path) {
        String caminoEnStorage = bar.getNombre() + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(caminoEnStorage);
        UploadTask uploadTask = imagenRef.putFile(path);
        uploadTask.addOnFailureListener(e -> {
        });
    }

    @Override
    public void subirBar() {
        baresRevisionRef.child(bar.getNombre()).setValue(bar);
    }
}
