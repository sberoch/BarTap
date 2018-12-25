package com.eriochrome.bartime.presenters;

import com.eriochrome.bartime.contracts.FavoritosFragmentContract;
import com.eriochrome.bartime.modelos.FavoritosFragmentInteraccion;

public class FavoritosFragmentPresenter implements FavoritosFragmentContract.CompleteListener {

    private FavoritosFragmentContract.Interaccion interaccion;
    private FavoritosFragmentContract.View view;

    public FavoritosFragmentPresenter() {
        interaccion = new FavoritosFragmentInteraccion(this);
    }

    public void bind(FavoritosFragmentContract.View view) {
        this.view = view;
    }

    public void unbind() {
        view = null;
    }

    @Override
    public void onSuccess() {
        view.finCargando(interaccion.obtenerBares());
    }


    public void mostrarFavoritos() {
        view.cargando();
        interaccion.mostrarBaresFavoritosConPalabra("");
    }

    public void buscar(String s) {
        view.cargando();
        interaccion.mostrarBaresFavoritosConPalabra(s);
    }
}