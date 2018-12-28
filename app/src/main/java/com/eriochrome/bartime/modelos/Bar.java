package com.eriochrome.bartime.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Bar implements Serializable {

    private String nombre;
    private String descripcion; //TODO: reemplazar por ubicacion
    private float estrellas;
    private long calificacionesAcumuladas;
    private int numeroDeCalificaciones;
    private int horarioInicial, horarioFinal;
    private int happyhourInicial;
    private int happuhourFinal;
    private ArrayList<String> metodosDePago;


    //Requerido por la base de datos.
    public Bar() {
    }

    public Bar(String nombre) {
        this.nombre = nombre;
        estrellas = 0;
        calificacionesAcumuladas = 0;
        numeroDeCalificaciones = 0;
        descripcion = "Test dis shit";
        horarioInicial = 0;
        horarioFinal = 23;
        happyhourInicial = 999;
        happuhourFinal = 999;
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
    public int getHorarioInicial() {
        return horarioInicial;
    }
    public int getHorarioFinal() {
        return horarioFinal;
    }
    public int getHappyhourInicial() {
        return happyhourInicial;
    }
    public int getHappuhourFinal() {
        return happuhourFinal;
    }
    public ArrayList<String> getMetodosDePago() {
        return metodosDePago;
    }

    public void actualizarEstrellas(int calificacion) {
        calificacionesAcumuladas += calificacion;
        numeroDeCalificaciones++;
        estrellas = (float)calificacionesAcumuladas / numeroDeCalificaciones;
    }

    public void agregarHorarios(int horarioInicial, int horarioFinal) {
        this.horarioInicial = horarioInicial;
        this.horarioFinal = horarioFinal;
    }

    public void agregarHappyhourHorarios(int happyhourInicial, int happyhourFinal) {
        this.happyhourInicial = happyhourInicial;
        this.happuhourFinal = happyhourFinal;
    }

    public void agregarMetodosDePago(ArrayList<String> metodosDePago) {
        this.metodosDePago = metodosDePago;
    }
}
