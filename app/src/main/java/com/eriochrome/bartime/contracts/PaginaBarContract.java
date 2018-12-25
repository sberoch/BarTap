package com.eriochrome.bartime.contracts;

        import com.eriochrome.bartime.modelos.Bar;

public interface PaginaBarContract {
    interface View {
        int getCalificacion();
    }

    interface Interaccion {
        void setBar(Bar bar);
        String getNombreDeBar();
        void actualizarEstrellas(int calificacion);
        void agregarAFavoritos();
    }
}
