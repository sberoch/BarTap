package com.eriochrome.bartime.modelos;

public class Filtro {

    boolean hayOferta;
    boolean abierto;
    boolean happyHour;
    String ordenamiento;

    public void ordenarSegun(String ordenamiento) {
        this.ordenamiento = ordenamiento;
    }

    public boolean filtroOferta() {
        return hayOferta;
    }

    public String getOrdenamiento() {
        return ordenamiento;
    }

    public boolean filtroAbierto() {
        return abierto;
    }

    public boolean filtroHappyHour() {
        return happyHour;
    }

    public void aplicarAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public void aplicarHappyHour(boolean happyHour) {
        this.happyHour = happyHour;
    }

    public void aplicarOfertas(boolean ofertas) {
        this.hayOferta = ofertas;
    }
}
