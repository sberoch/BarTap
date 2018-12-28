package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaginaBarInteraccion implements PaginaBarContract.Interaccion {

    private DatabaseReference ref;
    private DatabaseReference baresRef;
    private DatabaseReference favoritosRef;
    /*
    private DatabaseReference conOfertasRef = ref.child("baresConOferta");*/
    private FirebaseAuth auth;

    private Bar bar;

    public PaginaBarInteraccion() {
        ref = FirebaseDatabase.getInstance().getReference();
        baresRef = ref.child("bares");
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
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
        //TODO: agregar a bares con oferta tambien
        baresRef.child(bar.getNombre()).child("estrellas").setValue(bar.getEstrellas());
        baresRef.child(bar.getNombre()).child("calificacionesAcumuladas").setValue(bar.getCalificacionesAcumuladas());
        baresRef.child(bar.getNombre()).child("numeroDeCalificaciones").setValue(bar.getNumeroDeCalificaciones());
    }

    @Override
    public void agregarAFavoritos() {
        String nombreBar = bar.getNombre();
        favoritosRef.child(nombreBar).setValue(nombreBar);
    }
}
