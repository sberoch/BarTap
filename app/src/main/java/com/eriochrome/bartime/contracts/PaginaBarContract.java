package com.eriochrome.bartime.contracts;

        import com.eriochrome.bartime.modelos.Bar;

public interface PaginaBarContract {
    interface View {
        int getCalificacion();
        void agregadoAFavoritos();
        void quitadoDeFavoritos();
        void cargando();
        void finCargando();
    }

    interface Interaccion {
        void setBar(Bar bar);
        String getNombreDeBar();
        void actualizarEstrellas(int calificacion);
        void agregarAFavoritos();
        boolean hayUsuarioConectado();
        void quitarDeFavoritos();
        void checkearFavorito();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(boolean esFav);
    }
}
