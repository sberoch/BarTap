package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.DistincionContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DistincionInteraccion implements DistincionContract.Interaccion {

    private FirebaseAuth auth;
    private DatabaseReference refUsuarioBar;

    public DistincionInteraccion() {
        refUsuarioBar = FirebaseDatabase.getInstance().getReference().child("usuariosBar");
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void subirUsuarioBarADatabase() {
        FirebaseUser barAuth = auth.getCurrentUser();
        UsuarioBar barUsuario = new UsuarioBarBasico(barAuth.getDisplayName());
        refUsuarioBar.child(barAuth.getUid()).setValue(barUsuario);
    }
}
