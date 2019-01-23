package com.eriochrome.bartime.contracts;

import android.app.AlertDialog;
import android.location.Location;

import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.Filtro;

import java.util.ArrayList;

public interface BaresFragmentContract {

    interface Interaccion {
        void buscarConPalabra(String s);
        ArrayList<Bar> obtenerLista();
        void mostrarConFiltros(Filtro filtro);
        void setUltimaUbicacion(Location ultimaUbicacion);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<Bar> listaBares);
        String getOrdenamiento(AlertDialog dialog);

        boolean filtroOfertas(AlertDialog dialog);
        boolean filtroHappyHour(AlertDialog dialog);
        boolean filtroAbierto(AlertDialog dialog);
        boolean filtroEfectivo(AlertDialog dialog);
        boolean filtroCredito(AlertDialog dialog);
        boolean filtroDebito(AlertDialog dialog);
    }

    interface CompleteListener {
        void onSuccess();
    }
}
