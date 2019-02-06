package com.eriochrome.bartime.modelos.entidades;

public class Desafio extends Juego {

    private boolean permanente;
    private String desafioTexto;


    public Desafio(String desafioTexto) {
        this.desafioTexto = desafioTexto;
        this.permanente = false;
    }

    public String getDesafioTexto() {
        return desafioTexto;
    }

    public void asignarTipo() {
        super.asignarTipo("Desafio");
    }

    public void setID(String id) {
        super.setID(id);
    }

    public void setPermanente(boolean permanente) {
        this.permanente = permanente;
    }

    public boolean isPermanente() {
        return permanente;
    }

    /**
     * Requerido por firebase
     */
    public Desafio() {
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
