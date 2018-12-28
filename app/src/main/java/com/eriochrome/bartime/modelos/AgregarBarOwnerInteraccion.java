package com.eriochrome.bartime.modelos;

import android.net.Uri;

import com.eriochrome.bartime.contracts.AgregarBarOwnerContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AgregarBarOwnerInteraccion implements AgregarBarOwnerContract.Interaccion {

    private AgregarBarOwnerContract.CompleteListener listener;
    private Bar bar;
    private FirebaseUser authUser;
    private DatabaseReference refGlobal;
    private StorageReference storageReference;
    private Uri path;

    public AgregarBarOwnerInteraccion(AgregarBarOwnerContract.CompleteListener listener) {
        this.listener = listener;
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        refGlobal = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void crearBar(String textNombreBar) {
        bar = new Bar(textNombreBar);
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void subirBar() {
        listener.onStart();
        refGlobal.child("bares").child(bar.getNombre()).setValue(bar);
        refGlobal.child("usuariosBar").child(authUser.getUid()).child("barAsociado").setValue(bar)
                .addOnSuccessListener(aVoid -> listener.onSuccess());
    }

    @Override
    public void agregarFoto(Uri path) {
        this.path = path;
    }

    @Override
    public void subirFoto() {
        String caminoEnStorage = bar.getNombre() + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(caminoEnStorage);
        UploadTask uploadTask = imagenRef.putFile(path);
        uploadTask.addOnFailureListener(e -> {
            //TODO: throw
        });
    }


}