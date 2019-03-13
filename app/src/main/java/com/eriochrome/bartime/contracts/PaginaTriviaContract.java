package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Juego;

public interface PaginaTriviaContract {

    interface Interaccion {

        void setTrivia(Juego juego);
        void cargarDatosParticipantes();
        String getTipoDeJuego();
        String getResumen();
    }

    interface View {
        void cargando();
        void finCargando();
        void setGanadores(int ganadores);
        void setParticipantes(int participantes);
        void setTipoDeJuego(String tipoDeJuego);
        void setResumen(String resumen);
    }

    interface Listener {
        void onComplete(int participantes, int ganadores);
    }
}