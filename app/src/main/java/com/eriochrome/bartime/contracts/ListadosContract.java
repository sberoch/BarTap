package com.eriochrome.bartime.contracts;

public interface ListadosContract {
    interface View {
    }

    interface Interaccion {
        boolean estaConectado();
        void subirUsuarioADatabase();
        String getNombreUsuario();
    }
}
