package com.eriochrome.bartime.contracts;

        import com.eriochrome.bartime.modelos.Bar;
        import com.eriochrome.bartime.modelos.Comentario;

public interface PaginaBarContract {
    interface View {
        void agregadoAFavoritos();
        void quitadoDeFavoritos();
        void cargando();
        void finCargando();
        void comentarioListo();
        void yaCalificoElBar();
    }

    interface Interaccion {
        void setBar(Bar bar);
        String getNombreDeBar();
        void actualizarEstrellas(int calificacion);
        void agregarAFavoritos();
        boolean hayUsuarioConectado();
        void quitarDeFavoritos();
        void checkearFavorito();
        void enviarComentario(Comentario comentario);
        void checkearUsuarioCalificoBar();
        Bar getBar();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(boolean esFav);
        void comentarioListo();
        void yaCalificoEsteBar();
    }
}
