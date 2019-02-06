package com.eriochrome.bartime.modelos;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BarControlInteraccion implements BarControlContract.Interaccion {

    private FirebaseUser userAuth;
    private DatabaseReference refGlobal;
    private BarControlContract.CompleteListener listener;

    private Bar bar;
    private StorageReference storageReference;

    private ValueEventListener valueEventListenerAvisos = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChildren()) listener.hayAvisos();
            else listener.noHayAvisos();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };

    public BarControlInteraccion(BarControlContract.CompleteListener listener) {
        this.listener = listener;
        storageReference = FirebaseStorage.getInstance().getReference();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();
        refGlobal = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void setupBar() {
        listener.onStart();
        refGlobal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot barAsociadoPath = dataSnapshot.child("usuariosBar").child(userAuth.getUid()).child("barAsociado");
                if (barAsociadoPath.exists()) {
                    String nombreBarAsociado = barAsociadoPath.getValue(String.class);
                    bar = dataSnapshot.child("bares").child(nombreBarAsociado).getValue(Bar.class);
                    listener.onComplete(bar);
                } else {
                    listener.onComplete(null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public String getNombreBar() {
        return bar.getNombre();
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public void subirFoto(Uri path) {
        listener.onStart();
        String strNumeroDeFoto = "_" + Integer.toString(bar.getCantidadDeFotos() + 1);
        String caminoEnStorage = bar.getNombre() + strNumeroDeFoto + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(caminoEnStorage);
        UploadTask uploadTask = imagenRef.putFile(path);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            bar.aumentarCantidadDeFotos();
            refGlobal.child("bares").child(bar.getNombre()).child("cantidadDeFotos").setValue(bar.getCantidadDeFotos());
            listener.onComplete(bar);
        });
    }

    @Override
    public void checkearAvisos() {
        if (bar != null) {
            refGlobal.child("avisos").child(bar.getNombre())
                    .addValueEventListener(valueEventListenerAvisos);
        }
    }

    @Override
    public void dejarDeCheckearAvisos() {
        if (bar != null) {
            refGlobal.child("avisos").child(bar.getNombre())
                    .removeEventListener(valueEventListenerAvisos);
        }
    }

}