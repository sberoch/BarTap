package com.eriochrome.bartime.utils;

import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.ItemTienda;
import com.eriochrome.bartime.modelos.Juego;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreadorDeAvisos {

    private DatabaseReference ref;

    public CreadorDeAvisos() {
        ref = FirebaseDatabase.getInstance().getReference().child("avisos");
    }

    public void avisarGanadorDeJuego(Juego juego, String ganador) {

        String texto = getTextoGanadorDeJuego(juego);
        String key = ref.child(ganador).push().getKey();
        if (key != null) {
            ref.child(ganador).child(key).setValue(texto);
        }
    }

    public void avisarParticipacion(String nombreParticipante, Juego juego) {

        String texto =  getTextoParticipacion(juego, nombreParticipante);
        String key = ref.child(juego.getNombreBar()).push().getKey();
        if (key != null) {
            ref.child(juego.getNombreBar()).child(key).setValue(texto);
        }
    }


    public void avisarCompraDeDescuento(ItemTienda itemTienda, String displayName, Bar bar) {
        String texto = getTextoCompraDeDescuento(itemTienda, displayName);
        String key = ref.child(bar.getNombre()).push().getKey();
        if (key != null) {
            ref.child(bar.getNombre()).child(key).setValue(texto);
        }
    }


    private String getTextoGanadorDeJuego(Juego juego) {
        switch (juego.getTipoDeJuego()) {
            case "Desafio":
                return "Has ganado "
                        + juego.getPuntos()
                        + " puntos por ganar el desafio '"
                        + juego.getTextoResumen()
                        + "' en "
                        + juego.getNombreBar()
                        + ".";

            default:
                return "";
        }
    }


    private String getTextoParticipacion(Juego juego, String nombreParticipante) {
        switch (juego.getTipoDeJuego()) {
            case "Desafio":
                return nombreParticipante
                        + " esta ahora participando en el desafio '"
                        + juego.getTextoResumen()
                        + "'.";

            default:
                return "";
        }
    }


    private String getTextoCompraDeDescuento(ItemTienda itemTienda, String displayName) {
        return displayName
                + " ha comprado: '"
                + itemTienda.getDescripcion()
                + "'.";
    }
}
