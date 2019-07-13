package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.DatosBarReclamarContract;
import com.eriochrome.bartime.modelos.DatosBarReclamarInteraccion;

public class DatosBarReclamarPresenter {

    private DatosBarReclamarContract.Interaccion interaccion;
    private DatosBarReclamarContract.View view;

    public DatosBarReclamarPresenter() {
        interaccion = new DatosBarReclamarInteraccion();
    }

    public void bind(DatosBarReclamarContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

}