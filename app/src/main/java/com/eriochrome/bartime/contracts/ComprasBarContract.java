package com.eriochrome.bartime.contracts;

import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

import java.util.ArrayList;

public interface ComprasBarContract {

    interface Interaccion {
        void cargarCompras();
        void setBar(Bar bar);
        void eliminarComprobante(ComprobanteDeCompra comprobanteDeCompra);
    }

    interface View {
        void cargando();
        void finCargando(ArrayList<ComprobanteDeCompra> compras);
    }

    interface Listener {
        void listo(ArrayList<ComprobanteDeCompra> compras);
    }
}