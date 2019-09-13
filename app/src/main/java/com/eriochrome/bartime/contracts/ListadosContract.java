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
        void checkearAvisos();
        void dejarDeCheckearAvisos();
    }

    interface CompleteListener {
        void hayAvisos();
        void noHayAvisos();
    }
}
