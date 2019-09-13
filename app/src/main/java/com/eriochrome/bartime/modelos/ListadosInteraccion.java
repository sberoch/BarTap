package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.ListadosContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    }

    @Override
    public boolean estaConectado() {
        return auth.getCurrentUser() != null;
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
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            refUsuarios.child(uid).child("nombre").setValue(user.getDisplayName());
        }
    }

    @Override
    public String getNombreUsuario() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        } else {
            return "Invitado";
        }
    }
}
