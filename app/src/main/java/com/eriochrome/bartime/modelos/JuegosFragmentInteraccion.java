package com.eriochrome.bartime.modelos;

import androidx.annotation.NonNull;

import com.eriochrome.bartime.contracts.JuegosFragmentContract;
import com.eriochrome.bartime.modelos.entidades.Desafio;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Sorteo;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.eriochrome.bartime.utils.CreadorDeAvisos;
import com.eriochrome.bartime.utils.StrCompareUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JuegosFragmentInteraccion implements JuegosFragmentContract.Interaccion {

    private JuegosFragmentContract.Listener listener;
    private ArrayList<Juego> juegos;
    private DatabaseReference ref;
    private DatabaseReference refJuegos;
    private FirebaseUser authUser;
    private StrCompareUtils strComparer;

    public JuegosFragmentInteraccion(JuegosFragmentContract.Listener listener) {
        this.listener = listener;
        strComparer = new StrCompareUtils();
        juegos = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference();
        refJuegos = ref.child("juegos");
        authUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void mostrarJuegosConPalabra(String s) {
        String busqueda = s.toLowerCase();
        juegos.clear();
        refJuegos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.child("Sorteo").getChildren()) {
                    Juego juego = ds.getValue(Sorteo.class);
                    if (strComparer.juegoContiene(juego, busqueda)) {
                        juegos.add(juego);
                    }
                }
                for (DataSnapshot ds : dataSnapshot.child("Desafio").getChildren()) {
                    Juego juego = ds.getValue(Desafio.class);
                    if (strComparer.juegoContiene(juego, busqueda)) {
                        juegos.add(juego);
                    }
                }
                for (DataSnapshot ds : dataSnapshot.child("Trivia").getChildren()) {
                    Juego juego = ds.getValue(Trivia.class);
                    if (strComparer.juegoContiene(juego, busqueda)) {
                        juegos.add(juego);
                    }
                }
                listener.listo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    public ArrayList<Juego> obtenerJuegos() {
        return juegos;
    }

    /**
     * Chequea si se participo previamente en el juego.
     */
    @Override
    public void intentarParticiparDeJuego(Juego juego) {
        refJuegos.child(juego.getTipoDeJuego()).child(juego.getID()).child("participantes")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(authUser.getDisplayName())) {
                            listener.yaSeParticipo();
                        } else {
                            listener.participarDeJuego(juego);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }


    @Override
    public void participarDeJuego(Juego juego) {
        refJuegos.child(juego.getTipoDeJuego()).child(juego.getID()).child("participantes")
                .child(authUser.getDisplayName()).setValue(authUser.getDisplayName())
                .addOnSuccessListener(aVoid -> {

                    if (juego.getTipoDeJuego().equals("Trivia")) {
                        listener.ingresarATrivia((Trivia)juego);
                    } else {
                        listener.successParticipando();

                    }
                });

        //Si es sorteo hay que agregarlo a la seccion extra donde ademas se tiene en cuenta los invitados
        if (juego.getTipoDeJuego().equals("Sorteo")) {
            ref.child("invitadosSorteo").child(juego.getID()).child(authUser.getDisplayName()).setValue(0);
        }

        avisarParticipacion(juego);
    }

    private void avisarParticipacion(Juego juego) {
        CreadorDeAvisos creadorDeAvisos = new CreadorDeAvisos();
        creadorDeAvisos.avisarParticipacion(authUser.getDisplayName(), juego);
    }

    @Override
    public boolean estaConectado() {
        return authUser != null;
    }


}