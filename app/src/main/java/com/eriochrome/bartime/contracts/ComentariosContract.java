package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.Comentario;

import java.util.ArrayList;

public interface ComentariosContract {

    interface Interaccion {
        void setBar(Bar bar);
        void mostrarComentarios();
        ArrayList<Comentario> getListaComentarios();
    }


    interface View {
        void cargando();
        void finCargando(ArrayList<Comentario> comentarios);
    }

    interface CompleteListener {
        void onStart();
        void onSuccess();
    }
}