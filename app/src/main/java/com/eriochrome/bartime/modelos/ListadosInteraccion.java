package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.modelos.entidades.UsuarioAnonimo;
import com.eriochrome.bartime.modelos.entidades.UsuarioComun;
import com.eriochrome.bartime.modelos.entidades.UsuarioRegistrado;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListadosInteraccion implements ListadosContract.Interaccion {

    private final ListadosContract.CompleteListener listener;
    private FirebaseAuth auth;
    private DatabaseReference refGlobal;
    private DatabaseReference refUsuarios;
    private UsuarioComun usuario;

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.hasChildren()) listener.hayAvisos();
            else listener.noHayAvisos();
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) { }
    };


    public ListadosInteraccion(ListadosContract.CompleteListener listener) {
        this.listener = listener;
        auth = FirebaseAuth.getInstance();
        refGlobal = FirebaseDatabase.getInstance().getReference();
        refUsuarios = refGlobal.child("usuarios");

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
    public void conectar() {
        usuario = UsuarioRegistrado.crearConAuth(auth.getCurrentUser());
    }

    @Override
    public void checkearAvisos() {
        refGlobal.child("avisos").child(auth.getCurrentUser().getDisplayName())
                .addValueEventListener(valueEventListener);
    }

    @Override
    public void dejarDeCheckearAvisos() {
        refGlobal.child("avisos").child(auth.getCurrentUser().getDisplayName())
                .removeEventListener(valueEventListener);
    }

    @Override
    public void subirUsuarioADatabase() {
        refUsuarios.child(auth.getCurrentUser().getUid()).setValue(usuario);
    }

    @Override
    public String getNombreUsuario() {
        return usuario.getNombre();
    }

    @Override
    public void checkearNuevo() {
        refUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean esNuevo = dataSnapshot.hasChild(auth.getCurrentUser().getUid());
                listener.checkearNuevo(esNuevo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
