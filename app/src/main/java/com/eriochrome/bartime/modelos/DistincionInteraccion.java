package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.DistincionContract;
import com.eriochrome.bartime.modelos.entidades.UsuarioBar;
import com.eriochrome.bartime.modelos.entidades.UsuarioBarBasico;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DistincionInteraccion implements DistincionContract.Interaccion {

    private final DistincionContract.CompleteListener listener;
    private FirebaseAuth auth;
    private DatabaseReference refUsuariosBar;

    public DistincionInteraccion(DistincionContract.CompleteListener listener) {
        this.listener = listener;
        refUsuariosBar = FirebaseDatabase.getInstance().getReference().child("usuariosBar");
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void subirUsuarioBarADatabase() {
        FirebaseUser barAuth = auth.getCurrentUser();
        UsuarioBar barUsuario = new UsuarioBarBasico(barAuth.getDisplayName());
        refUsuariosBar.child(barAuth.getUid()).setValue(barUsuario);
    }

    @Override
    public void checkearExiste() {
        refUsuariosBar.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean existe = dataSnapshot.hasChild(auth.getCurrentUser().getUid());
                listener.checkearExiste(existe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
