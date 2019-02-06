package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public interface CrearTriviaContract {

    interface Interaccion {
        void setBar(Bar bar);
        Bar getBar();
        Trivia getTrivia();
        void comenzarCreacionTrivia(String titulo, int cantPreguntas);
    }

    interface View {
        String getTitulo();
        int getCantPreguntas();
    }
}