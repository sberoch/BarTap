package com.eriochrome.bartime.contracts;

public interface LauncherContract {

    interface View {
        void startNuevo();
        void startUsuario();
        void startBar();
    }

    interface Interaccion {
        boolean estaConectado();
        void esBar();
    }

    interface CompleteListener {
        void esBar(boolean esBar);
    }
}
