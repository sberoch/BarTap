package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.DistincionContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DistincionInteraccion implements DistincionContract.Interaccion {

    private FirebaseAuth auth;
    private DatabaseReference refUsuarios;

    public DistincionInteraccion() {
        refUsuarios = FirebaseDatabase.getInstance().getReference().child("usuarios");
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void subirUsuarioBarADatabase() {
        FirebaseUser barAuth = auth.getCurrentUser();
        Usuario barUsuario = new UsuarioBar(barAuth.getDisplayName());
        refUsuarios.child(barAuth.getUid()).setValue(barUsuario);
    }
}
