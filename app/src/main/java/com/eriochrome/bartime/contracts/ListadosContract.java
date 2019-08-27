package com.eriochrome.bartime.contracts;

import android.net.Uri;

public interface ListadosContract {
    interface View {
        void hayAvisos();
        void noHayAvisos();

        void setInvUrl(Uri shortLink);
    }

    interface Interaccion {
        boolean estaConectado();
        void subirUsuarioADatabase();
        String getNombreUsuario();
        void checkearNuevo();
        void conectar();
        void checkearAvisos();
        void dejarDeCheckearAvisos();

        void mockCompartirConDynLink();
    }

    interface CompleteListener {
        void checkearNuevo(boolean esNuevoUsuario);
        void hayAvisos();
        void noHayAvisos();

        void setInvUrl(Uri shortLink);
    }
}
