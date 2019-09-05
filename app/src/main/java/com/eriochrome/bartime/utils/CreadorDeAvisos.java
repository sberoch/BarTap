package com.eriochrome.bartime.utils;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Sorteo;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreadorDeAvisos {

    private DatabaseReference refAvisos;

    public CreadorDeAvisos() {
        refAvisos = FirebaseDatabase.getInstance().getReference().child("avisos");
    }

    public void avisarGanadorDeJuego(Juego juego, String ganador) {

        String texto = juego.getTextoGanadorDeJuego();
        String key = refAvisos.child(ganador).push().getKey();
        if (key != null) {
            refAvisos.child(ganador).child(key).setValue(texto);
        }
    }


    public void avisarParticipacion(String nombreParticipante, Juego juego) {
        if (!juego.getTipoDeJuego().equals("Trivia")) {

            String texto = juego.getTextoParticipacion(nombreParticipante);
            String key = refAvisos.child(juego.getNombreBar()).push().getKey();
            if (key != null) {
                refAvisos.child(juego.getNombreBar()).child(key).setValue(texto);
            }
        }
    }


    public void avisarCompraDeDescuento(ItemTienda itemTienda, String nombreComprador, Bar bar) {
        String texto = getTextoCompraDeDescuento(itemTienda, nombreComprador);
        String key = refAvisos.child(bar.getNombre()).push().getKey();
        if (key != null) {
            refAvisos.child(bar.getNombre()).child(key).setValue(texto);
        }
    }


    private String getTextoCompraDeDescuento(ItemTienda itemTienda, String nombreComprador) {
        return nombreComprador
                + " ha comprado: '"
                + itemTienda.getDescripcion()
                + "'.";
    }

    public void avisarPerdedorDeSorteo(Sorteo sorteo, String perdedor) {
        String texto = getTextoPerderSorteo(sorteo);
        String key = refAvisos.child(perdedor).push().getKey();
        if (key != null) {
            refAvisos.child(perdedor).child(key).setValue(texto);
        }
    }

    private String getTextoPerderSorteo(Sorteo sorteo) {
        return "No resultaste ganador del sorteo por "
                + sorteo.getPuntos()
                + " puntos del bar "
                + sorteo.getNombreBar()
                + ".";
    }

    public void avisarComentarioEnBar(String user, String nombreBar) {
        String texto = getTextoComentarioBar(user);
        String key = refAvisos.child(nombreBar).push().getKey();
        if (key != null) {
            refAvisos.child(nombreBar).child(key).setValue(texto);
        }
    }

    private String getTextoComentarioBar(String user) {
        return "¡"
               + user
               + " ha calificado tu bar!";
    }
}
