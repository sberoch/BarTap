package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public class TriviaInteraccion implements TriviaContract.Interaccion {

    private Trivia trivia;

    @Override
    public void setTrivia(Trivia trivia) {
        this.trivia = trivia;
    }
}