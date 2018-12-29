package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.utils.ActualizadorFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaginaBarInteraccion implements PaginaBarContract.Interaccion {

    private final PaginaBarContract.CompleteListener listener;
    private DatabaseReference ref;
    private DatabaseReference baresRef;
    private DatabaseReference favoritosRef;
    //private DatabaseReference conOfertasRef = ref.child("baresConOferta");
    private FirebaseAuth auth;

    private Bar bar;

    public PaginaBarInteraccion(PaginaBarContract.CompleteListener listener) {
        this.listener = listener;
        ref = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        if (hayUsuarioConectado()) {
            favoritosRef = ref.child("usuarios").child(auth.getCurrentUser().getUid()).child("favoritos");
        }
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public String getNombreDeBar() {
        return bar.getNombre();
    }

    @Override
    public void actualizarEstrellas(int calificacion) {
        bar.actualizarEstrellas(calificacion);
        ActualizadorFirebase.actualizarEstrellas(bar, ref);
    }

    @Override
    public boolean hayUsuarioConectado() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public void agregarAFavoritos() {
        String nombreBar = bar.getNombre();
        favoritosRef.child(nombreBar).setValue(nombreBar);
    }

    @Override
    public void quitarDeFavoritos() {
        favoritosRef.child(bar.getNombre()).removeValue();
    }

    @Override
    public void checkearFavorito() {
        listener.onStart();
        favoritosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean esFav = dataSnapshot.hasChild(bar.getNombre());
                listener.onComplete(esFav);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
