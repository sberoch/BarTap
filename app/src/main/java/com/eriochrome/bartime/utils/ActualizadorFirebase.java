package com.eriochrome.bartime.utils;

import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DatabaseReference;

public class ActualizadorFirebase {

    public static void actualizarEstrellas(Bar bar, DatabaseReference ref) {
        //Bares general
        DatabaseReference baresRef = ref.child("bares");
        baresRef.child(bar.getNombre()).child("estrellas").setValue(bar.getEstrellas());
        baresRef.child(bar.getNombre()).child("calificacionesAcumuladas").setValue(bar.getCalificacionesAcumuladas());
        baresRef.child(bar.getNombre()).child("numeroDeCalificaciones").setValue(bar.getNumeroDeCalificaciones());

        //Bares en oferta
        DatabaseReference ofertaRef = ref.child("baresEnOferta");
        ofertaRef.child(bar.getNombre()).child("estrellas").setValue(bar.getEstrellas());
        ofertaRef.child(bar.getNombre()).child("calificacionesAcumuladas").setValue(bar.getCalificacionesAcumuladas());
        ofertaRef.child(bar.getNombre()).child("numeroDeCalificaciones").setValue(bar.getNumeroDeCalificaciones());
    }
}
