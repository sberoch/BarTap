package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.CrearTriviaContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Trivia;

public class CrearTriviaInteraccion implements CrearTriviaContract.Interaccion {

    private Bar bar;
    private Trivia trivia;

    public CrearTriviaInteraccion() {
        trivia = new Trivia();
    }

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public Bar getBar() {
        return bar;
    }

    @Override
    public Trivia getTrivia() {
        return trivia;
    }

    @Override
    public void comenzarCreacionTrivia(String titulo, int cantPreguntas) {
        trivia.setTitulo(titulo);
        trivia.setCantPreguntas(cantPreguntas);
    }
}