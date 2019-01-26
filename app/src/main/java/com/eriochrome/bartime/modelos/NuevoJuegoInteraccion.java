package com.eriochrome.bartime.modelos;

import com.eriochrome.bartime.contracts.NuevoJuegoContract;

public class NuevoJuegoInteraccion implements NuevoJuegoContract.Interaccion {

    private Bar bar;

    @Override
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    @Override
    public Bar getBar() {
        return bar;
    }
}