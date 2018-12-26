package com.eriochrome.bartime.modelos;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UsuarioRegistrado extends Usuario {

    public UsuarioRegistrado() {
    }

    public UsuarioRegistrado(String nombre) {
        this.nombre = nombre;
        this.esBar = false;
    }

    public static UsuarioRegistrado crearConAuth(FirebaseUser currentUser) {
        return new UsuarioRegistrado(currentUser.getDisplayName());
    }

}
