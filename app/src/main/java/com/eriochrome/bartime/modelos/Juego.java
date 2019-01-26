package com.eriochrome.bartime.modelos;

public class Juego {

    protected int puntos;
    private String nombreBar;
    private String tipoDeJuego;

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

}
