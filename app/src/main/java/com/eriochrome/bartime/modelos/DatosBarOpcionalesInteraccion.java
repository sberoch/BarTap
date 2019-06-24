package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.DatosBarOpcionalesContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DatosBarOpcionalesInteraccion implements DatosBarOpcionalesContract.Interaccion {

    private Bar bar;
    private DatabaseReference baresRef;
    private StorageReference storageReference;
    private DatosBarOpcionalesContract.Listener listener;

    public DatosBarOpcionalesInteraccion(DatosBarOpcionalesContract.Listener listener) {
        this.listener = listener;
        baresRef = FirebaseDatabase.getInstance().getReference().child("bares");
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void setMetodosDePago(ArrayList<String> metodosDePago) {
        bar.agregarMetodosDePago(metodosDePago);
    }

    @Override
    public void subirDatos() {
        baresRef.child(bar.getNombre()).setValue(bar).addOnSuccessListener(aVoid -> {
            listener.listo();
        });
    }

    @Override
    public void subirImagenPrincipal() {
        //TODO: subir imagen principal
    }
}