package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.MiPerfilContract;
import com.eriochrome.bartime.modelos.MiPerfilInteraccion;

public class MiPerfilPresenter {

    private MiPerfilContract.Interaccion interaccion;
    private MiPerfilContract.View view;

    public MiPerfilPresenter() {
        interaccion = new MiPerfilInteraccion();
    }

    public void bind(MiPerfilContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}