package com.eriochrome.bartime.modelos.entidades;

import java.util.ArrayList;

public class Trivia {
    private ArrayList<PreguntaTrivia> preguntas;
    private int cantPreguntas;
    private String titulo;

    public Trivia() {
    }

    public ArrayList<PreguntaTrivia> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<PreguntaTrivia> preguntas) {
        this.preguntas = preguntas;
    }

    public int getCantPreguntas() {
        return cantPreguntas;
    }

    public void setCantPreguntas(int cantPreguntas) {
        this.cantPreguntas = cantPreguntas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
