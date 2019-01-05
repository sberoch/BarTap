package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.ComentariosContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComentariosInteraccion implements ComentariosContract.Interaccion {

    private Bar bar;
    private ComentariosContract.CompleteListener listener;
    private DatabaseReference refComentarios;
    private ArrayList<Comentario> listaComentarios;

    public ComentariosInteraccion(ComentariosContract.CompleteListener listener) {
        this.listener = listener;
        listaComentarios = new ArrayList<>();
        refComentarios = FirebaseDatabase.getInstance().getReference().child("comentarios");
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void mostrarComentarios() {
        listener.onStart();
        refComentarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nombreBar = ds.child("nombreBar").getValue(String.class);
                    if (nombreBar.equals(bar.getNombre())) {
                        listaComentarios.add(ds.getValue(Comentario.class));
                    }
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public ArrayList<Comentario> getListaComentarios() {
        return listaComentarios;
    }
}