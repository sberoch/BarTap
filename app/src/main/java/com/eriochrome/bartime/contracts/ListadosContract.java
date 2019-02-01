package com.eriochrome.bartime.contracts;

public interface ListadosContract {
    interface View {
        void hayAvisos();
        void noHayAvisos();
    }

    interface Interaccion {
        boolean estaConectado();
        void subirUsuarioADatabase();
        String getNombreUsuario();
        void checkearNuevo();
        void conectar();
        void checkearAvisos();
    }

    interface CompleteListener {
        void checkearNuevo(boolean esNuevoUsuario);
        void hayAvisos();
        void noHayAvisos();
    }
}
