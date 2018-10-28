package com.eriochrome.bartime;

class Bar {

    private String nombre;
    private String descripcion;
    private int estrellas;
    //Todo: super rancio, mock
    private int numeroDeFoto;


    public Bar(String nombre, String descripcion, int numeroDeFoto, int estrellas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.numeroDeFoto = numeroDeFoto;
        this.estrellas = estrellas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNumeroDeFoto() {
        return numeroDeFoto;
    }

    public int getEstrellas() {
        return estrellas;
    }
}
