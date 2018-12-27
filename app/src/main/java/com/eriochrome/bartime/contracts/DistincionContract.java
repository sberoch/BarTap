package com.eriochrome.bartime.contracts;

public interface DistincionContract {

    interface View {
        void loginBar();
    }

    interface Interaccion {
        void subirUsuarioBarADatabase();
        void checkearEsNuevo();
    }

    interface CompleteListener {
        void checkearNuevo(boolean esNuevo);
    }
}
