package com.eriochrome.bartime.modelos.entidades;

import com.google.firebase.auth.FirebaseUser;

public class UsuarioRegistrado extends UsuarioComun {

    public UsuarioRegistrado() {
    }

    public UsuarioRegistrado(String nombre) {
        this.nombre = nombre;
    }

    public static UsuarioRegistrado crearConAuth(FirebaseUser currentUser) {
        return new UsuarioRegistrado(currentUser.getDisplayName());
    }

}
