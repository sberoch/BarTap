package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

public interface CrearDesafioContract {

    interface Interaccion {
        void setBar(Bar bar);
        void enviarDesafio(String desafio, String dificultad, boolean unicoGanador);
    }

    interface View {
        String getDesafioText();
        String getDificultad();
        void enviado();
        boolean esDeUnicoGanador();
    }

    interface Callback {
        void enviado();
    }
}