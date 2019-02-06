package com.eriochrome.bartime.modelos.entidades;

import java.io.Serializable;

public abstract class UsuarioComun implements Serializable {

    protected String nombre;

    public String getNombre() {
        return nombre;
    }
}
