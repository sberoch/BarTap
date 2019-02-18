package com.eriochrome.bartime.modelos.entidades;

import java.io.Serializable;

public abstract class Juego implements Serializable {

    protected int puntos;
    private String nombreBar;
    private String tipoDeJuego;
    private String id;

    public int getPuntos() {
        return puntos;
    }
    public void asignarPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getNombreBar() {
        return nombreBar;
    }
    public void asignarNombreBar(String nombreBar) {
        this.nombreBar = nombreBar;
    }

    public String getTipoDeJuego() {
        return tipoDeJuego;
    }
    protected void asignarTipo(String tipo) {
        this.tipoDeJuego = tipo;
    }

    public String getTextoResumen() {
        if (this instanceof Desafio) {
            return ((Desafio) this).getDesafioTexto();
        }
        if (this instanceof Trivia) {
            return ((Trivia) this).getTitulo();
        }
        return "";
    }

    public String getID() {
        return id;
    }
    public void setID(String id) {
        this.id = id;
    }

    public abstract String getTextoParticipacion(String nombreParticipante);
    public abstract String getTextoGanadorDeJuego();

}
