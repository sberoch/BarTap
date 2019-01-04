package com.eriochrome.bartime.modelos;

public class Comentario {

    private int estrellas;
    private String comentarioText;
    private String comentador;
    private String nombreBar;
    private String comentarioID;

    public String getID() {
        return comentarioID;
    }
    public String getNombreBar() {
        return nombreBar;
    }
    public int getEstrellas() {
        return estrellas;
    }
    public String getComentarioText() {
        return comentarioText;
    }
    public String getComentador() {
        return comentador;
    }

    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }
    public void setComentarioText(String comentarioText) {
        this.comentarioText = comentarioText;
    }
    public void setComentador(String comentador) {
        this.comentador = comentador;
    }
    public void setNombreBar(String nombreBar) {
        this.nombreBar = nombreBar;
    }
    public void setID(String comentarioID) {
        this.comentarioID = comentarioID;
    }
}
