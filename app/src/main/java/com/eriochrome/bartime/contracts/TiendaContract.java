package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;

import java.util.ArrayList;

public interface TiendaContract {

    interface Interaccion {
        void setBar(Bar bar);
        void setupTienda();
        void guardarPuntos(Integer misPuntos);
        int getPuntos();
        void comprarItem(ItemTienda itemTienda);
        void dejarDeEscucharCambios();
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<ItemTienda> items, Integer misPuntos);
    }

    interface Listener {
        void listo(ArrayList<ItemTienda> items, Integer misPuntos);
    }
}