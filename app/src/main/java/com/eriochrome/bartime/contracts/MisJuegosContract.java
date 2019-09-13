package com.eriochrome.bartime.contracts;

import android.net.Uri;

import com.eriochrome.bartime.modelos.entidades.Juego;

import java.util.ArrayList;

public interface MisJuegosContract {

    interface Interaccion {
        void mostrarJuegos();
        void dejarDeParticipar(Juego juego);
        void invitarASorteo(Juego juego);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Juego> juegos);
        void setInvitationUrl(Uri shortLink);
    }

    interface Listener {
        void listo(ArrayList<Juego> juegos);
        void setInvitationUrl(Uri shortLink);
    }
}