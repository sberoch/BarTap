package com.eriochrome.bartime.contracts;

import android.app.AlertDialog;

import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.Filtro;

import java.util.ArrayList;

public interface BaresFragmentContract {

    interface Interaccion {
        void buscarConPalabra(String s);
        ArrayList<Bar> obtenerLista();
        void mostrarConFiltros(Filtro filtro);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Bar> listaBares);
        boolean hayOfertas(AlertDialog dialog);
        String getOrdenamiento(AlertDialog dialog);

    }

    interface CompleteListener {
        void onSuccess();
    }
}
