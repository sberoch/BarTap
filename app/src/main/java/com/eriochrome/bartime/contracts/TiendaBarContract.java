package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.ItemTienda;

import java.util.ArrayList;

public interface TiendaBarContract {

    interface Interaccion {
        void mostrarItemsTienda();
        void setBar(Bar bar);
        void crearItem(ItemTienda itemTienda);
        void eliminarItem(ItemTienda itemTienda);
        void dejarDeMostrarItems();
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<ItemTienda> items);
    }

    interface Listener {
        void listo(ArrayList<ItemTienda> items);
    }
}