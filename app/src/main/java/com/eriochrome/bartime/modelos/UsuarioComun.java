package com.eriochrome.bartime.modelos;

import java.io.Serializable;

public abstract class UsuarioComun implements Serializable {

    protected String nombre;

    public String getNombre() {
        return nombre;
    }
}
