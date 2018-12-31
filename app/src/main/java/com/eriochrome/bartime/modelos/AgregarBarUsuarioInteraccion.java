package com.eriochrome.bartime.modelos;

import android.net.Uri;

import com.eriochrome.bartime.contracts.AgregarBarUsuarioContract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AgregarBarUsuarioInteraccion implements AgregarBarUsuarioContract.Interaccion {

    private DatabaseReference baresRef;
    private StorageReference storageReference;

   public AgregarBarUsuarioInteraccion() {
       baresRef = FirebaseDatabase.getInstance().getReference().child("bares");
       storageReference = FirebaseStorage.getInstance().getReference();
   }

    @Override
    public void agregarBar(Bar nuevoBar, Uri path) {
        String caminoEnStorage = nuevoBar.getNombre() + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(caminoEnStorage);
        UploadTask uploadTask = imagenRef.putFile(path);
        uploadTask.addOnFailureListener(e -> {
        });

        baresRef.child(nuevoBar.getNombre()).setValue(nuevoBar, (databaseError, databaseReference) -> {
            if (databaseError != null) {
            }
        });

    }
}
