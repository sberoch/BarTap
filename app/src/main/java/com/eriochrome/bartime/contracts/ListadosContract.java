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
        void checkearAvisos();
        void dejarDeCheckearAvisos();

        void mockCompartirConDynLink();
    }

    interface CompleteListener {
        void hayAvisos();
        void noHayAvisos();

        void setInvUrl(Uri shortLink);
    }
}
