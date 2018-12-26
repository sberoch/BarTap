package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListadosInteraccion implements ListadosContract.Interaccion {

    private FirebaseAuth auth;
    private DatabaseReference refUsuarios;
    private UsuarioComun usuario;

    //TODO: revisar esto de login
    public ListadosInteraccion() {
        auth = FirebaseAuth.getInstance();
        refUsuarios = FirebaseDatabase.getInstance().getReference().child("usuarios");

        if (estaConectado()) {
            usuario = UsuarioRegistrado.crearConAuth(auth.getCurrentUser());
        } else {
            usuario = new UsuarioAnonimo();
        }
    }

    @Override
    public boolean estaConectado() {
        return auth.getCurrentUser() != null;
    }

    @Override
    public void subirUsuarioADatabase() {
        FirebaseUser userAuth = auth.getCurrentUser();
        usuario = UsuarioRegistrado.crearConAuth(userAuth);
        refUsuarios.child(userAuth.getUid()).setValue(usuario);
    }

    @Override
    public String getNombreUsuario() {
        return usuario.getNombre();
    }
}
