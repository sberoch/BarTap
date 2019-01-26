package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;

public interface CrearDesafioContract {

    interface Interaccion {
        void setBar(Bar bar);
        void enviarDesafio(String desafio, String dificultad);
    }

    interface View {
        String getDesafioText();
        String getDificultad();
        void enviado();
    }

    interface Callback {
        void enviado();
    }
}