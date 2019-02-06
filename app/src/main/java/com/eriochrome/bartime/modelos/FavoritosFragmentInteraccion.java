package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.FavoritosFragmentContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
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
    private FirebaseUser authUser;

    private ArrayList<Bar> listaBares;

    private FavoritosFragmentContract.CompleteListener listener;

    public FavoritosFragmentInteraccion(FavoritosFragmentContract.CompleteListener listener) {
        this.listener = listener;
        listaBares = new ArrayList<>();
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        refGlobal = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public ArrayList<Bar> obtenerBares() {
        return listaBares;
    }


    /**
     * Por cada favorito, si contiene la palabra "s", convierte el String en un Bar y lo agrega a la lista
     * Pasar "" para buscar todos los favoritos.
     * Puede ser lento - O(n^2) -
     */
    @Override
    public void mostrarBaresFavoritosConPalabra(String s) {
        String palabra = s.toLowerCase();
        listaBares.clear();
        refGlobal.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> favoritos = new ArrayList<>();
                DataSnapshot dsFavoritos = dataSnapshot.child("usuarios").child(authUser.getUid()).child("favoritos");
                for (DataSnapshot ds : dsFavoritos.getChildren()) {
                    favoritos.add(ds.getValue(String.class));
                }
                for (String nombreFavorito : favoritos) {
                    if (nombreFavorito.toLowerCase().contains(palabra)) {
                        Bar bar = dataSnapshot.child("bares").child(nombreFavorito).getValue(Bar.class);
                        listaBares.add(bar);
                    }
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}