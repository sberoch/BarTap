package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.AvisosContract;
import com.eriochrome.bartime.modelos.entidades.Aviso;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvisosInteraccion implements AvisosContract.Interaccion {

    private AvisosContract.Listener listener;
    private ArrayList<Aviso> avisos;
    private DatabaseReference refAvisos;
    private DatabaseReference refGlobal;
    private FirebaseUser authUser;

    public AvisosInteraccion(AvisosContract.Listener listener) {
        this.listener = listener;
        avisos = new ArrayList<>();
        refGlobal = FirebaseDatabase.getInstance().getReference();
        refAvisos = refGlobal.child("avisos");
        authUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void cargarAvisos(boolean esBar) {

        if (esBar) {
            cargarAvisosBar();
        } else {
            cargarAvisosUsuario();
        }
    }

    private void cargarAvisosUsuario() {
        refAvisos.child(authUser.getDisplayName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String avisoTexto = ds.getValue(String.class);
                    String avisoID = ds.getKey();
                    avisos.add(new Aviso(avisoTexto, avisoID));
                }
                listener.listo(avisos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


    private void cargarAvisosBar() {
        refGlobal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombreBar = dataSnapshot.child("usuariosBar")
                        .child(authUser.getUid()).child("barAsociado").getValue(String.class);

                DataSnapshot listaAvisosSnap = dataSnapshot.child("avisos").child(nombreBar);
                for (DataSnapshot ds : listaAvisosSnap.getChildren()) {
                    String avisoTexto = ds.getValue(String.class);
                    String avisoID = ds.getKey();
                    avisos.add(new Aviso(avisoTexto, avisoID));
                }
                listener.listo(avisos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void quitarItem(String idItem, boolean esBar) {
        if (esBar) {
            removerAviso(idItem);
        } else {
            refAvisos.child(authUser.getDisplayName()).child(idItem).setValue(null);
        }
    }


    private void removerAviso(String idItem) {
        refGlobal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombreBar = dataSnapshot.child("usuariosBar")
                        .child(authUser.getUid()).child("barAsociado").getValue(String.class);

                listener.quitarItemBar(idItem, nombreBar);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }


    @Override
    public void quitarConNombreBar(String idItem, String nombreBar) {
        refAvisos.child(nombreBar).child(idItem).setValue(null);
    }
}