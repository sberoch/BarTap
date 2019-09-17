package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Juego;

public interface ListadosContract {
    interface View {
        void hayAvisos();
        void noHayAvisos();
        void abrirSorteo(Juego juego);

    }

    interface Interaccion {
        boolean estaConectado();
        void subirUsuarioADatabase();
        String getNombreUsuario();
        void checkearAvisos();
        void dejarDeCheckearAvisos();
		void anotarReferrer(String referrerUid, String gameID);
        void obtenerSorteoConId(String gameID);
        void anotarConNombre(String nombre, String gameID);
    }

    interface CompleteListener {
        void hayAvisos();
        void noHayAvisos();
        void abrirSorteo(Juego juego);
        void anotarConNombre(String nombre, String gameID);

    }
}
