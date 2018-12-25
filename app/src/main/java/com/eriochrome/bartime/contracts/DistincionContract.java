package com.eriochrome.bartime.contracts;

public interface DistincionContract {

    interface View {
        void loginBar();
    }

    interface Interaccion {
        void subirUsuarioBarADatabase();
    }
}
