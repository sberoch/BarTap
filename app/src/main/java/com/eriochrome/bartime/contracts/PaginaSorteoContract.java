package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Juego;

public interface PaginaSorteoContract {

    interface Interaccion {

        void setSorteo(Juego juego);
        void sortear();
        String getResumenJuego();
        void cargarCantidadDeParticipantes();
        void borrarSorteo();
    }

    interface View {

        void cargando();
        void finSorteo(String participanteGanador);
        void setResumenJuego(String resumenJuego);
        void setCantParticipantes(int cantParticipantes);
        void finCargando();
    }

    interface Listener {

        void finSorteo(String participanteGanador);
        void setCantParticipantes(long childrenCount);
    }
}