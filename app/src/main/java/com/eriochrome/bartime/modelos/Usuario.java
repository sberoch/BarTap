package com.eriochrome.bartime.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    protected String nombre;
    protected boolean esBar;

    public String getNombre() {
        return nombre;
    }

    public boolean esBar() {
        return esBar;
    }
}
