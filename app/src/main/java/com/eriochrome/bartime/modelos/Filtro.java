package com.eriochrome.bartime.modelos;

public class Filtro {

    boolean hayOferta;
    String ordenamiento;

    public Filtro(boolean hayOferta) {
        this.hayOferta = hayOferta;
    }

    public void ordenarSegun(String ordenamiento) {
        this.ordenamiento = ordenamiento;
    }

    public boolean hayOferta() {
        return hayOferta;
    }

    public String getOrdenamiento() {
        return ordenamiento;
    }
}
