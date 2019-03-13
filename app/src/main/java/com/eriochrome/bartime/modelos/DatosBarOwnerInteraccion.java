package com.eriochrome.bartime.modelos;

import android.net.Uri;

import com.eriochrome.bartime.contracts.DatosBarOwnerContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class DatosBarOwnerInteraccion implements DatosBarOwnerContract.Interaccion {

    private DatosBarOwnerContract.CompleteListener listener;
    private Bar bar;
    private FirebaseUser authUser;
    private DatabaseReference refGlobal;
    private StorageReference storageReference;
    private Uri path;

    public DatosBarOwnerInteraccion(DatosBarOwnerContract.CompleteListener listener) {
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
        refGlobal.child("usuariosBar").child(authUser.getUid()).child("barAsociado").setValue(bar.getNombre())
                .addOnSuccessListener(aVoid -> listener.onSuccess())
                .addOnFailureListener(e -> {
                    throw new RuntimeException();
                });
    }

    @Override
    public void agregarFoto(Uri path) {
        this.path = path;
    }

    /**
     * Suposicion: el bar no cambia de nombre
     */
    @Override
    public void editarBar() {
        DatabaseReference refBar = refGlobal.child("bares").child(bar.getNombre());
        refBar.child("ubicacion").setValue(bar.getUbicacion());
        refBar.child("lat").setValue(bar.getLat());
        refBar.child("lng").setValue(bar.getLng());
        refBar.child("horariosInicial").setValue(bar.getHorariosInicial());
        refBar.child("horariosFinal").setValue(bar.getHorariosFinal());
        refBar.child("happyhourInicial").setValue(bar.getHappyhourInicial());
        refBar.child("happyhourFinal").setValue(bar.getHappyhourFinal());
        refBar.child("metodosDePago").setValue(bar.getMetodosDePago());

    }

    @Override
    public void setUbicacion(String direccion, double lat, double lng) {
        bar.setUbicacion(direccion);
        bar.setLatLng(lat,lng);
    }

    @Override
    public String getDireccion() {
        return bar.getUbicacion();
    }

    @Override
    public void subirFoto() throws RuntimeException {
        String caminoEnStorage = bar.getNombre() + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(caminoEnStorage);
        UploadTask uploadTask = imagenRef.putFile(path);
        uploadTask.addOnFailureListener(e -> {
            throw new RuntimeException();
        });
    }


}