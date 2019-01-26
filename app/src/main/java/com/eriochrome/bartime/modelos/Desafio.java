package com.eriochrome.bartime.modelos;

public class Desafio extends Juego{

    private String desafioTexto;

    public Desafio(String desafioTexto) {
        this.desafioTexto = desafioTexto;
    }

    public String getDesafioTexto() {
        return desafioTexto;
    }

    public void asignarTipo() {
        super.asignarTipo("Desafio");
    }


    /**
     * Requerido por firebase
     */
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
