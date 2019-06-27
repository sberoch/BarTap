package com.eriochrome.bartime.modelos;

import android.app.usage.NetworkStats;

import com.eriochrome.bartime.contracts.DatosBarOpcionalesContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class DatosBarOpcionalesInteraccion implements DatosBarOpcionalesContract.Interaccion {

    private Bar bar;
    private DatabaseReference refGlobal;
    private DatabaseReference baresRef;
    private StorageReference storageReference;
    private DatosBarOpcionalesContract.Listener listener;
    private FirebaseUser authUser;

    public DatosBarOpcionalesInteraccion(DatosBarOpcionalesContract.Listener listener) {
        this.listener = listener;
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        refGlobal = FirebaseDatabase.getInstance().getReference();
        baresRef = refGlobal.child("bares");
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
        refGlobal.child("usuariosBar").child(authUser.getUid()).child("barAsociado").setValue(bar.getNombre());
        baresRef.child(bar.getNombre()).setValue(bar).addOnSuccessListener(aVoid -> {
            listener.listo();
        });
    }
}