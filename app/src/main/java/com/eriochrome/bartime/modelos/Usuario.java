package com.eriochrome.bartime.modelos;

import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;

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
