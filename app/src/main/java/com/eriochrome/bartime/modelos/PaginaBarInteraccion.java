package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaginaBarInteraccion implements PaginaBarContract.Interaccion {

    private DatabaseReference ref;
    private DatabaseReference baresRef;
    private DatabaseReference favoritosRef;
    /*
    private DatabaseReference conOfertasRef = ref.child("baresConOferta");*/
    private FirebaseAuth auth;

    private Bar bar;

    public PaginaBarInteraccion() {
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        baresRef = ref.child("bares");
        favoritosRef = ref.child("usuarios").child(auth.getCurrentUser().getUid()).child("favoritos");
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
        //TODO: agregar a bares con oferta tambien
        baresRef.child(bar.getNombre()).child("estrellas").setValue(bar.getEstrellas());
        baresRef.child(bar.getNombre()).child("calificacionesAcumuladas").setValue(bar.getCalificacionesAcumuladas());
        baresRef.child(bar.getNombre()).child("numeroDeCalificaciones").setValue(bar.getNumeroDeCalificaciones());
    }

    @Override
    public void agregarAFavoritos() {
        favoritosRef.child(bar.getNombre()).setValue(bar);
    }
}
