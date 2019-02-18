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

    @Override
    public String getTextoParticipacion(String nombreParticipante) {
        return nombreParticipante
                + " esta ahora participando en el desafio '"
                + desafioTexto
                + "'.";
    }

    @Override
    public String getTextoGanadorDeJuego() {
        String nombreBar = getNombreBar();
        return "Has ganado "
                + puntos
                + " puntos por ganar el desafio '"
                + desafioTexto
                + "' en "
                + nombreBar
                + ".";
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
