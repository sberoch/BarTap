package com.eriochrome.bartime.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

public class ActualizadorFirebase {

    public static void actualizarEstrellas(Bar bar, DatabaseReference ref) {
        DatabaseReference baresRef = ref.child("bares");
        baresRef.child(bar.getNombre()).child("estrellas").setValue(bar.getEstrellas());
        baresRef.child(bar.getNombre()).child("calificacionesAcumuladas").setValue(bar.getCalificacionesAcumuladas());
        baresRef.child(bar.getNombre()).child("numeroDeCalificaciones").setValue(bar.getNumeroDeCalificaciones());
    }

    public static void actualizarPuntos(String ganador, String nombreBar, int puntos, DatabaseReference refGlobal) {
        refGlobal.child("puntos").child(ganador).child(nombreBar)
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                        Integer valorActual = mutableData.getValue(Integer.class);
                        if (valorActual == null) {
                            mutableData.setValue(puntos);
                        }
                        else {
                            mutableData.setValue(valorActual + puntos);
                        }
                        return Transaction.success(mutableData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                    }
                });
    }
}
