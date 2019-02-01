package com.eriochrome.bartime.utils;

import com.eriochrome.bartime.modelos.Juego;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreadorDeAvisos {

    private DatabaseReference ref;

    public CreadorDeAvisos() {
        ref = FirebaseDatabase.getInstance().getReference().child("avisos");
    }

    public void avisarGanadorDeJuego(Juego juego, String ganador) {

        String texto = getTextoSegunTipoDeJuego(juego);
        String key = ref.child(ganador).push().getKey();
        if (key != null) {
            ref.child(ganador).child(key).setValue(texto);
        }
    }





    private String getTextoSegunTipoDeJuego(Juego juego) {
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
}
