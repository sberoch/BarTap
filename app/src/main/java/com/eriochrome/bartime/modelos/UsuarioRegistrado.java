package com.eriochrome.bartime.modelos;

import com.google.firebase.auth.FirebaseUser;

public class UsuarioRegistrado extends Usuario {

    public UsuarioRegistrado(String nombre) {
        this.nombre = nombre;
        esBar = false;
    }

    public static Usuario crearConAuth(FirebaseUser currentUser) {
        return new UsuarioRegistrado(currentUser.getDisplayName());
    }
}
