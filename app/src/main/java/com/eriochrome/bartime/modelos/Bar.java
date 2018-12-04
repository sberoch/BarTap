package com.eriochrome.bartime.modelos;

import java.io.Serializable;

public class Bar implements Serializable {

    private String nombre;
    private String descripcion;
    private float estrellas;
    private long calificacionesAcumuladas;
    private int numeroDeCalificaciones;


    //Requerido por la base de datos.
    public Bar() {
    }

    public Bar(String nombre) {
        this.nombre = nombre;
        estrellas = 0;
        calificacionesAcumuladas = 0;
        numeroDeCalificaciones = 0;
        descripcion = "Test dis shit";
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

    public long getCalificacionesAcumuladas() {
        return calificacionesAcumuladas;
    }

    public int getNumeroDeCalificaciones() {
        return numeroDeCalificaciones;
    }

    public void actualizarEstrellas(int calificacion) {
        calificacionesAcumuladas += calificacion;
        numeroDeCalificaciones++;
        estrellas = (float)calificacionesAcumuladas / numeroDeCalificaciones;
    }
}
