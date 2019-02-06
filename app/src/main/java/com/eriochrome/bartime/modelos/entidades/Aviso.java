package com.eriochrome.bartime.modelos.entidades;

public class Aviso {
    private String textoAviso;
    private String id;

    public Aviso(String textoAviso, String id) {
        this.textoAviso = textoAviso;
        this.id = id;
    }

    public String getTextoAviso() {
        return textoAviso;
    }

    public String getId() {
        return id;
    }
}
