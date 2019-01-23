package com.eriochrome.bartime.modelos;

import android.location.Location;
import android.support.annotation.NonNull;

import com.eriochrome.bartime.contracts.BaresFragmentContract;
import com.eriochrome.bartime.utils.ComparadorBaresDistancia;
import com.google.android.gms.maps.model.LatLng;
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

    private LatLng ubicacion;
    private boolean ordenPorDistancia;


    public BaresFragmentInteraccion(BaresFragmentContract.CompleteListener listener) {
        ordenDescendente = false;
        ordenPorDistancia = false;
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
        Query query = obtenerOrdenamiento(filtro);
        listaBares.clear();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot barSnap : dataSnapshot.getChildren()) {
                    Bar bar = barSnap.getValue(Bar.class);
                    if ((bar != null) && (bar.contieneFiltros(filtro))) {
                        listaBares.add(bar);
                    }
                }
                if (ordenDescendente) {
                    Collections.reverse(listaBares);
                }
                if (ordenPorDistancia) {
                    Collections.sort(listaBares, new ComparadorBaresDistancia(ubicacion));
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    @Override
    public void setUltimaUbicacion(Location ultimaUbicacion) {
        ubicacion = new LatLng(ultimaUbicacion.getLatitude(), ultimaUbicacion.getLongitude());
    }


    private Query obtenerOrdenamiento(Filtro filtro) {
        ordenDescendente = false;
        ordenPorDistancia = false;
        Query query = refBares;

        switch (filtro.getOrdenamiento()) {
            case "distancia":
                ordenPorDistancia = true;
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
