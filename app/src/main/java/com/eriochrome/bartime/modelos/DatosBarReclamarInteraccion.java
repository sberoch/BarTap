package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.DatosBarReclamarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.presenters.DatosBarReclamarPresenter;
import com.eriochrome.bartime.utils.StrCompareUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class DatosBarReclamarInteraccion implements DatosBarReclamarContract.Interaccion {

    private DatosBarReclamarContract.Listener listener;
    private FirebaseUser authUser;
    private StrCompareUtils strComparer;
    private DatabaseReference ref;
    private ArrayList<Bar> bares;
    private Bar nuevoBar;

    public DatosBarReclamarInteraccion(DatosBarReclamarContract.Listener listener) {
        this.listener = listener;
        strComparer = new StrCompareUtils();
        ref = FirebaseDatabase.getInstance().getReference().child("bares");
        authUser = FirebaseAuth.getInstance().getCurrentUser();
        bares = new ArrayList<>();
    }

    @Override
    public void mostrarBaresParaReclamar() {
        bares.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Bar bar = ds.getValue(Bar.class);
                    if (bar.getOwner().equals("")) {
                        if (strComparer.sonParecidos(nuevoBar.getNombre(), bar.getNombre())) {
                            bares.add(bar);
                        }
                    }
                }
                listener.listo(bares);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void setBar(Bar bar) {
        nuevoBar = bar;
    }

    @Override
    public Bar getBar() {
        return nuevoBar;
    }

    @Override
    public void reclamarBar(Bar bar) {
        nuevoBar.reclamar(bar);
        nuevoBar.setOwner(authUser.getDisplayName());
        ref.child(bar.getNombre()).removeValue();
    }
}