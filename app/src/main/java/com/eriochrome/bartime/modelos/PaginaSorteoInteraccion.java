package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.PaginaSorteoContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Sorteo;
import com.eriochrome.bartime.utils.ActualizadorFirebase;
import com.eriochrome.bartime.utils.CreadorDeAvisos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Set;

public class PaginaSorteoInteraccion implements PaginaSorteoContract.Interaccion {

    private HashMap<String, Integer> participantes;
    private Sorteo sorteo;
    private PaginaSorteoContract.Listener listener;
    private DatabaseReference ref;

    public PaginaSorteoInteraccion(PaginaSorteoContract.Listener listener) {
        this.listener = listener;
        participantes = new HashMap<>();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void setSorteo(Juego juego) {
        this.sorteo = (Sorteo) juego;
    }

    @Override
    public void sortear() {
        ref.child("invitadosSorteo").child(sorteo.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nombre = ds.getKey();
                    Integer invitados = ds.getValue(Integer.class);
                    participantes.put(nombre, invitados);
                }
                String ganador = sortear(participantes);

                enviarAvisos(ganador, participantes.keySet());
                ActualizadorFirebase.actualizarPuntos(ganador, sorteo.getNombreBar(), sorteo.getPuntos(), ref);

                listener.finSorteo(ganador);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void enviarAvisos(String ganador, Set<String> participantes) {
        //Al ganador
        CreadorDeAvisos creadorDeAvisos = new CreadorDeAvisos();
        creadorDeAvisos.avisarGanadorDeJuego(sorteo, ganador);

        //A los perdedores
        for (String perdedor : participantes) {
            if (!perdedor.equals(ganador)) {
                creadorDeAvisos.avisarPerdedorDeSorteo(sorteo, perdedor);
            }
        }
    }

    @Override
    public String getResumenJuego() {
        return sorteo.getTextoResumen();
    }

    @Override
    public void cargarCantidadDeParticipantes() {
        ref.child("invitadosSorteo").child(sorteo.getID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listener.setCantParticipantes(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public void borrarSorteo() {
        ref.child("invitadosSorteo").child(sorteo.getID()).removeValue();
        ref.child("juegos").child("Sorteo").child(sorteo.getID()).removeValue();
    }

    private String sortear(HashMap<String, Integer> participantes) {
        int totalPuntos = 0;
        for (Integer invitados : participantes.values()) {
            totalPuntos += (1 + invitados);
        }

        double randnum = Math.random();
        double probabilidadAcumulada = 0.0;
        for (HashMap.Entry<String, Integer> par : participantes.entrySet()) {
            double probItem = ((double) (par.getValue() + 1)) / totalPuntos;
            probabilidadAcumulada += probItem;
            if (randnum <= probabilidadAcumulada) {
                return par.getKey();
            }
        }
        return null;
    }
}