package com.eriochrome.bartime.presenters;

import android.content.Intent;

import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.modelos.Comentario;
import com.eriochrome.bartime.modelos.PaginaBarInteraccion;

import java.util.ArrayList;


public class PaginaBarPresenter implements PaginaBarContract.CompleteListener {

    private PaginaBarContract.Interaccion interaccion;
    private PaginaBarContract.View view;
    private boolean esFav;

    public PaginaBarPresenter() {
        interaccion = new PaginaBarInteraccion(this);
    }

    public void bind(PaginaBarContract.View view) {
        this.view = view;
    }

    public void unbind(){
        view = null;
    }

    public void obtenerBar(Intent intent) {
        Bar bar = (Bar) intent.getSerializableExtra("bar");
        interaccion.setBar(bar);
    }

    public String getNombreDeBar() {
        return interaccion.getNombreDeBar();
    }

    public void enviarComentario(Comentario comentario) {
        interaccion.enviarComentario(comentario);
        calificarBar(comentario.getEstrellas());
    }

    private void calificarBar(int calificacion) {
        interaccion.actualizarEstrellas(calificacion);
    }

    public boolean esFavorito() {
        return esFav;
    }

    public void agregarAFavoritos() {
        interaccion.agregarAFavoritos();
        checkeoFavorito();
    }

    public void quitarDeFavoritos() {
        interaccion.quitarDeFavoritos();
        checkeoFavorito();
    }

    public boolean hayUsuarioConectado() {
        return interaccion.hayUsuarioConectado();
    }

    public void checkeoFavorito() {
        interaccion.checkearFavorito();
    }

    @Override
    public void onStart() {
        view.cargando();
    }

    @Override
    public void onComplete(boolean esFav) {
        view.finCargando();
        if (esFav) {
            view.agregadoAFavoritos();
        } else {
            view.quitadoDeFavoritos();
        }
        this.esFav = esFav;
    }

    @Override
    public void comentarioListo() {
        view.comentarioListo();
    }

    public void checkearUsuarioCalificoBar() {
        interaccion.checkearUsuarioCalificoBar();
    }

    @Override
    public void yaCalificoEsteBar() {
        view.yaCalificoElBar();
    }

    @Override
    public void cargaDeComentarios() {
        view.cargaDeComentarios();
    }

    @Override
    public void finCargaDeComentarios() {
        view.finCargaDeComentarios();
    }

    public Intent enviarBar(Intent i) {
        return i.putExtra("bar", interaccion.getBar());
    }

    public ArrayList<Comentario> getComentarios() {
        return interaccion.getComentarios();
    }

    public void cargarComentarios() {
        interaccion.cargarComentarios();
    }

    public Bar getBar() {
        return interaccion.getBar();
    }
}
