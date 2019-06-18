package com.eriochrome.bartime.contracts;

        import com.eriochrome.bartime.modelos.entidades.Bar;
        import com.eriochrome.bartime.modelos.entidades.Comentario;

        import java.util.ArrayList;

public interface PaginaBarContract {
    interface View {
        void agregadoAFavoritos();
        void quitadoDeFavoritos();
        void cargando();
        void finCargando();
        void comentarioListo();
        void yaCalificoElBar();
        void cargaDeComentarios();
        void finCargaDeComentarios();
        void setPuntos(Integer puntos);
        void onImageLoaded(String path);
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
        ArrayList<Comentario> getComentarios();
        void cargarComentarios();
        void cargarPuntosEnElBar();
        void cargarImagenes();
    }

    interface CompleteListener {
        void onStart();
        void onComplete(boolean esFav);
        void comentarioListo();
        void yaCalificoEsteBar();
        void cargaDeComentarios();
        void finCargaDeComentarios();
        void setPuntos(Integer puntos);
        void onImageLoaded(String toString);
    }
}
