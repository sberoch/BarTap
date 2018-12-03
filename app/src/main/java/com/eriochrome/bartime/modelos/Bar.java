package com.eriochrome.bartime.modelos;

import java.io.Serializable;

public class Bar implements Serializable {

    private String nombre;
    private String descripcion;
    private double estrellas;
    private String imagePath;

    //Requerido por la base de datos.
    public Bar() {
    }

    public Bar(String nombre) {
        this.nombre = nombre;
        estrellas = 0;
        descripcion = "Test dis shit";
    }


    public Bar(String nombre, String descripcion, double estrellas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estrellas = estrellas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getEstrellas() {
        return estrellas;
    }

    public void setImagePath(String caminoEnStorage) {
        this.imagePath = caminoEnStorage;
    }

    public String getImagePath() {
        return imagePath;
    }
}
