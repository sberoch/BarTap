package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.ComprasBarContract;
import com.eriochrome.bartime.modelos.ComprasBarInteraccion;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

import java.util.ArrayList;

public class ComprasBarPresenter implements ComprasBarContract.Listener{

    private ComprasBarContract.Interaccion interaccion;
    private ComprasBarContract.View view;

    public ComprasBarPresenter() {
        interaccion = new ComprasBarInteraccion(this);
    }

    public void bind(ComprasBarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    public void cargarCompras() {
        view.cargando();
        interaccion.cargarCompras();
    }

    @Override
    public void listo(ArrayList<ComprobanteDeCompra> compras) {
        view.finCargando(compras);
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar)intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public void eliminarComprobante(ComprobanteDeCompra comprobanteDeCompra) {
        interaccion.eliminarComprobante(comprobanteDeCompra);
    }
}