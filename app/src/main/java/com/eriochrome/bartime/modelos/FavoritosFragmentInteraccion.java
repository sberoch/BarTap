package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.FavoritosFragmentContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FavoritosFragmentInteraccion implements FavoritosFragmentContract.Interaccion {

    private DatabaseReference refGlobal;
    private DatabaseReference refBares;
    private DatabaseReference refFavoritos;
    private FirebaseUser authUser;

    private ArrayList<Bar> listaBares;
    private UsuarioRegistrado usuario;

    private FavoritosFragmentContract.CompleteListener listener;

    public FavoritosFragmentInteraccion(FavoritosFragmentContract.CompleteListener listener) {
        this.listener = listener;
        listaBares = new ArrayList<>();

        authUser = FirebaseAuth.getInstance().getCurrentUser();

        refGlobal = FirebaseDatabase.getInstance().getReference();
        refBares = refGlobal.child("bares");
        refFavoritos = refGlobal.child("usuarios").child(authUser.getUid()).child("favoritos");

        usuario = UsuarioRegistrado.crearConAuth(authUser);
        descargarFavoritos(usuario);
    }


    @Override
    public ArrayList<Bar> obtenerBares() {
        return listaBares;
    }

    /**
     * Por cada bar, se fija en toda la lista de nombres y lo agrega si es igual y si contiene a "s"
     * Pasar "" si se quieren buscar todos los favoritos
     * Puede ser lento - O(n^2) -
     */
    @Override
    public void mostrarBaresFavoritosConPalabra(String s) {
        String palabra = s.toLowerCase();
        listaBares.clear();
        refBares.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bar bar = ds.getValue(Bar.class);
                    for (String nombreBarFavorito : usuario.getFavoritos()) {
                        if (nombreBarFavorito.equals(bar.getNombre()) && (nombreBarFavorito.toLowerCase().contains(palabra))) {
                            listaBares.add(bar);
                        }
                    }
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO: throw
            }
        });
    }


    //TODO: probablemente necesite un onCompleteListener
    private void descargarFavoritos(UsuarioRegistrado usuario) {
        refFavoritos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    usuario.addFavorito(ds.getValue(Bar.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO: throw
            }
        });
    }
}