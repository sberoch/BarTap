package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.MisComprasContract;
import com.eriochrome.bartime.modelos.MisComprasInteraccion;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

import java.util.ArrayList;

public class MisComprasPresenter implements MisComprasContract.Listener {

    private MisComprasContract.Interaccion interaccion;
    private MisComprasContract.View view;

    public MisComprasPresenter() {
        interaccion = new MisComprasInteraccion(this);
    }

    public void bind(MisComprasContract.View view) {
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
}