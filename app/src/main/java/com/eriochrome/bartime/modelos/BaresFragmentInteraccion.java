package com.eriochrome.bartime.modelos;

import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.BaresFragmentContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class BaresFragmentInteraccion implements BaresFragmentContract.Interaccion {

    private DatabaseReference refGlobal;
    private DatabaseReference refBares;
    private boolean ordenDescendente;
    private ArrayList<Bar> listaBares;


    private BaresFragmentContract.CompleteListener listener;


    public BaresFragmentInteraccion(BaresFragmentContract.CompleteListener listener) {
        ordenDescendente = false;
        refGlobal = FirebaseDatabase.getInstance().getReference();
        refBares = refGlobal.child("bares");
        listaBares = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public void buscarConPalabra(String s) {
        final String busqueda = s.toLowerCase();
        listaBares.clear();
        refBares.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nombreBar = ds.child("nombre").getValue(String.class).toLowerCase();
                    if (nombreBar.contains(busqueda)) {
                        listaBares.add(ds.getValue(Bar.class));
                    }
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public ArrayList<Bar> obtenerLista() {
        return listaBares;
    }

    @Override
    public void mostrarConFiltros(Filtro filtro) {
        Query query = obtenerQuery(filtro);
        listaBares.clear();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot barSnap : dataSnapshot.getChildren()) {
                    listaBares.add(barSnap.getValue(Bar.class));
                }
                if(ordenDescendente) {
                    Collections.reverse(listaBares);
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    /**
     * Achica el query segun los datos marcados en el dialog.
     *
     * NOTAR: hay bares que estan en otra rama del json en firebase para poder combinar filtros.
     */
    private Query obtenerQuery(Filtro filtro) {
        ordenDescendente = false;
        Query query = refBares;
        if (filtro.hayOferta()) {
            query = refGlobal.child("baresConOferta");
        }
        switch (filtro.getOrdenamiento()) {
            case "distancia":
                //TODO: distancias (geoloc)
                query = query.orderByChild("estrellas");
                break;
            case "estrellas":
                query = query.orderByChild("estrellas");
                ordenDescendente = true;
                break;
            case "nombre":
                query = query.orderByChild("nombre");
                break;
        }
        return query;
    }
}
