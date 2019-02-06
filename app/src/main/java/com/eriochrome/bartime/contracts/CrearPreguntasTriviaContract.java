package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public interface CrearPreguntasTriviaContract {

    interface Interaccion {

        void setBar(Bar bar);
        void setTrivia(Trivia trivia);
        boolean quedanPreguntas();
        void guardarPregunta(String pregunta, String opcionA, String opcionB, String opcionC, String correcta);
        void subirTrivia();
    }

    interface View {
        void ultimaPregunta();
        String getPregunta();
        String getOpcionA();
        String getOpcionB();
        String getOpcionC();
        String getRespuestaCorrecta();
        void enviado();
    }

    interface Listener {
        void enviado();
    }
}