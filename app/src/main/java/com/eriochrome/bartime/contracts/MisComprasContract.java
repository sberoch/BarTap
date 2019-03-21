package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

import java.util.ArrayList;

public interface MisComprasContract {

    interface Interaccion {

        void cargarCompras();
    }

    interface View {

        void cargando();
        void finCargando(ArrayList<ComprobanteDeCompra> compras);
    }

    interface Listener {

        void listo(ArrayList<ComprobanteDeCompra> compras);
    }
}