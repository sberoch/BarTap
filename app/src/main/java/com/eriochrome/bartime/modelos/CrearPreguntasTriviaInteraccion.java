package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.CrearPreguntasTriviaContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.PreguntaTrivia;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearPreguntasTriviaInteraccion implements CrearPreguntasTriviaContract.Interaccion {

    private Bar bar;
    private Trivia trivia;
    private int preguntasRestantes;
    private DatabaseReference refJuegos;
    private CrearPreguntasTriviaContract.Listener listener;

    public CrearPreguntasTriviaInteraccion(CrearPreguntasTriviaContract.Listener listener) {
        this.listener = listener;
        refJuegos = FirebaseDatabase.getInstance().getReference().child("juegos");
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
        this.preguntasRestantes = trivia.getCantPreguntas();
    }

    @Override
    public boolean quedanPreguntas() {
        preguntasRestantes--;
        return preguntasRestantes > 0;
    }

    @Override
    public void guardarPregunta(String pregunta, String opcionA, String opcionB, String opcionC, String correcta) {
        PreguntaTrivia preguntaTrivia = new PreguntaTrivia(pregunta, opcionA, opcionB, opcionC, correcta);
        trivia.addPregunta(preguntaTrivia);
    }

    @Override
    public void subirTrivia() {
        int puntos = trivia.getCantPreguntas() * 50;
        trivia.asignarPuntos(puntos);
        trivia.asignarNombreBar(bar.getNombre());
        trivia.asignarTipo();

        String id = refJuegos.push().getKey();
        trivia.setID(id);

        if (id != null) {
            refJuegos.child("Trivia").child(id).setValue(trivia)
                    .addOnSuccessListener(aVoid -> listener.enviado());
        }
    }

}