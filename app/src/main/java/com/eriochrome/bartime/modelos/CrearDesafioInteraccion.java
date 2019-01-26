package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.CrearDesafioContract;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearDesafioInteraccion implements CrearDesafioContract.Interaccion {

    private CrearDesafioContract.Callback listener;
    private Bar bar;
    private DatabaseReference refJuegos;

    public CrearDesafioInteraccion(CrearDesafioContract.Callback listener) {
        this.listener = listener;
        refJuegos = FirebaseDatabase.getInstance().getReference().child("juegos");
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void enviarDesafio(String desafioTexto, String dificultad) {

        Desafio desafio = new Desafio(desafioTexto);

        int puntos = getPuntosConDificultad(dificultad);
        desafio.asignarPuntos(puntos);
        desafio.asignarNombreBar(bar.getNombre());
        desafio.asignarTipo();

        String desafioID = refJuegos.push().getKey();
        if (desafioID != null) {
            refJuegos.child(desafioID).setValue(desafio)
                    .addOnSuccessListener(aVoid -> listener.enviado());
        }

    }


    private int getPuntosConDificultad(String dificultad) {
        switch (dificultad) {
            case "Facil":
                return 100;

            case "Medio":
                return 200;

            case "Dificil":
                return 300;

            default:
                return 0;
        }
    }
}