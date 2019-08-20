package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;

public interface CrearSorteoContract {

    interface Interaccion {

        void enviarSorteo(String fechaFin, String puntos);
        void setBar(Bar bar);
    }

    interface View {

        String getFechaFin();
        String getPuntos();
        void enviado();
    }

    interface Listener {

        void enviado();
    }
}