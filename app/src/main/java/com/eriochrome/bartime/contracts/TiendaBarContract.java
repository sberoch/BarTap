package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;

import java.util.ArrayList;

public interface TiendaBarContract {

    interface Interaccion {
        void mostrarItemsTienda();
        void setBar(Bar bar);
        void crearItem(ItemTienda itemTienda);
        void eliminarItem(ItemTienda itemTienda);
        void dejarDeMostrarItems();
        Bar getBar();
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<ItemTienda> items);
    }

    interface Listener {
        void listo(ArrayList<ItemTienda> items);
    }
}