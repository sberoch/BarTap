package com.eriochrome.bartime.modelos.entidades;

public class Sorteo extends Juego {

    private String fechaFin;

    public String getFechaFin() {
        return fechaFin;
    }

    public void setID(String id) {
        super.setID(id);
    }

    public void asignarTipo() {
        super.asignarTipo("Sorteo");
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public String getTextoParticipacion(String nombreParticipante) {
        return nombreParticipante
                + " esta ahora participando en el sorteo por "
                + puntos
                + " puntos.";
    }

    @Override
    public String getTextoGanadorDeJuego() {
        String nombreBar = getNombreBar();
        return "¡Has ganado el sorteo de "
                + nombreBar
                + " por "
                + puntos
                + "!";
    }

    public String getDescripcionSorteo() {
        return "Sorteo por "
                + puntos
                + " puntos. Finaliza el "
                + fechaFin;
    }


    /**
     * Requerido por firebase
     */
    public Sorteo() {
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
