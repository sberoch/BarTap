package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.PreguntaTrivia;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public interface TriviaContract {

    interface Interaccion {
        void setTrivia(Trivia trivia);
        PreguntaTrivia cargarSiguiente();
        boolean eligioOpcionCorrecta(String opcion);
        void actualizarPuntos();
        boolean quedanPreguntas();
        void agregarGanador();
    }

    interface View {
        void llenar(String pregunta, String opA, String opB, String opC);
    }
}