package com.eriochrome.bartime.modelos;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class UsuarioRegistrado extends Usuario {

    private ArrayList<String> favoritos;

    public UsuarioRegistrado() {
    }

    public UsuarioRegistrado(String nombre) {
        this.nombre = nombre;
        this.esBar = false;
        this.favoritos = new ArrayList<>();
    }

    public static UsuarioRegistrado crearConAuth(FirebaseUser currentUser) {
        return new UsuarioRegistrado(currentUser.getDisplayName());
    }

    public ArrayList<String> getFavoritos() {
        return favoritos;
    }

    public void addFavorito(Bar bar) {
        favoritos.add(bar.getNombre());
    }

}
