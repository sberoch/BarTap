package com.eriochrome.bartime.modelos;

public class Bar {

    private String nombre;
    private String descripcion;
    private double estrellas;
    //Todo: super rancio, mock
    private int numeroDeFoto;


    public Bar(String nombre, String descripcion, int numeroDeFoto, double estrellas) {
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

    public double getEstrellas() {
        return estrellas;
    }
}
