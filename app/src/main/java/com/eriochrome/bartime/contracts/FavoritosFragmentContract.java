package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;

import java.util.ArrayList;

public interface FavoritosFragmentContract {

    interface Interaccion {
        ArrayList<Bar> obtenerBares();
        void mostrarBaresFavoritosConPalabra(String s);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Bar> listaBares);
    }

    interface CompleteListener {
        void onSuccess();
    }
}