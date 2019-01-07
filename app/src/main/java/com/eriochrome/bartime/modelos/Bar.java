package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.utils.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Bar implements Serializable {

    private String nombre;
    private String ubicacion; //TODO: reemplazar por ubicacion
    private float estrellas;
    private long calificacionesAcumuladas;
    private int numeroDeCalificaciones;
    private HashMap<String, Integer> horariosInicial;
    private HashMap<String, Integer> horariosFinal;
    private HashMap<String, Integer> happyhourInicial;
    private HashMap<String, Integer> happyhourFinal;
    private ArrayList<String> metodosDePago;


    //Requerido por la base de datos.
    public Bar() {
    }

    public Bar(String nombre) {
        this.nombre = nombre;
        estrellas = 0;
        calificacionesAcumuladas = 0;
        numeroDeCalificaciones = 0;
        ubicacion = "Test dis shit";
        horariosInicial = inicializarHorarios();
        horariosFinal = inicializarHorarios();
        happyhourInicial = inicializarHorarios();
        happyhourFinal = inicializarHorarios();
        metodosDePago = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }
    public String getUbicacion() {
        return ubicacion;
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
    public HashMap<String, Integer> getHorariosInicial() {
        return horariosInicial;
    }
    public HashMap<String, Integer> getHorariosFinal() {
        return horariosFinal;
    }
    public HashMap<String, Integer> getHappyhourInicial() {
        return happyhourInicial;
    }
    public HashMap<String, Integer> getHappyhourFinal() {
        return happyhourFinal;
    }
    public ArrayList<String> getMetodosDePago() {
        return metodosDePago;
    }

    public void actualizarEstrellas(int calificacion) {
        calificacionesAcumuladas += calificacion;
        numeroDeCalificaciones++;
        estrellas = (float)calificacionesAcumuladas / numeroDeCalificaciones;
    }

    public void agregarHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        this.horariosInicial = horariosInicial;
        this.horariosFinal = horariosFinal;
    }

    public void agregarHappyhourHorarios(HashMap<String, Integer> happyhourInicial, HashMap<String, Integer> happyhourFinal) {
        this.happyhourInicial = happyhourInicial;
        this.happyhourFinal = happyhourFinal;
    }

    public void agregarMetodosDePago(ArrayList<String> metodosDePago) {
        this.metodosDePago = metodosDePago;
    }

    public boolean estaAbierto() {
        Calendar ahora = Calendar.getInstance();
        ahora.setTime(new Date());
        String diaDeHoy = Utils.getStringDiaDeSemana(ahora);
        return Utils.estaEntreHoras(horariosInicial.get(diaDeHoy), horariosFinal.get(diaDeHoy), ahora);
    }

    public boolean hayHappyHour() {
        Calendar ahora = Calendar.getInstance();
        ahora.setTime(new Date());
        String diaDeHoy = Utils.getStringDiaDeSemana(ahora);
        return Utils.estaEntreHoras(happyhourInicial.get(diaDeHoy), happyhourFinal.get(diaDeHoy), ahora);
    }


    private HashMap<String, Integer> inicializarHorarios() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo", 0);
        devolver.put("Lunes", 0);
        devolver.put("Martes", 0);
        devolver.put("Miercoles", 0);
        devolver.put("Jueves", 0);
        devolver.put("Viernes", 0);
        devolver.put("Sabado", 0);
        return devolver;
    }

}
