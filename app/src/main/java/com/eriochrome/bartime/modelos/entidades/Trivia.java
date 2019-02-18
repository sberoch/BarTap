package com.eriochrome.bartime.modelos.entidades;

import java.util.ArrayList;

public class Trivia extends Juego {
    private ArrayList<PreguntaTrivia> preguntas;
    private int cantPreguntas;
    private String titulo;

    public ArrayList<PreguntaTrivia> getPreguntas() {
        return preguntas;
    }
    public void setPreguntas(ArrayList<PreguntaTrivia> preguntas) {
        this.preguntas = preguntas;
    }
    public void addPregunta(PreguntaTrivia preguntaTrivia) {
        this.preguntas.add(preguntaTrivia);
    }

    public int getCantPreguntas() {
        return cantPreguntas;
    }
    public void setCantPreguntas(int cantPreguntas) {
        this.cantPreguntas = cantPreguntas;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void asignarTipo() {
        super.asignarTipo("Trivia");
    }

    @Override
    public String getTextoParticipacion(String nombreParticipante) {
        //No se avisa la participacion en una trivia
        return "";
    }

    @Override
    public String getTextoGanadorDeJuego() {
        String nombreBar = getNombreBar();
        return "Has ganado "
                + puntos
                + " puntos por ganar la trivia '"
                + titulo
                + "' en "
                + nombreBar
                + ".";
    }

    /**
     * Requerido por firebase
     */
    public Trivia() {
        preguntas = new ArrayList<>();
    }
    @Override
    public int getPuntos() {
        return super.getPuntos();
    }
    @Override
    public String getTipoDeJuego() {
        return super.getTipoDeJuego();
    }
    @Override
    public String getNombreBar() {
        return super.getNombreBar();
    }
}
