package com.eriochrome.bartime.vistas;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.modelos.Comentario;
import com.eriochrome.bartime.presenters.PaginaBarPresenter;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class PaginaBarActivity extends AppCompatActivity implements PaginaBarContract.View, DialogComentario.ComentarioListener {

    private TextView nombreBar;
    private Button calificarBar;
    private Button favorito;

    private ProgressBar progressBar;

    private PaginaBarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_bar);

        presenter = new PaginaBarPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        nombreBar = findViewById(R.id.nombre_bar);
        calificarBar = findViewById(R.id.calificarBar);
        favorito = findViewById(R.id.agregar_favorito);

        nombreBar.setText(presenter.getNombreDeBar());
        setupListeners();

        //TODO: cambiar los toast por dialogs que permitan crear cuenta
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter.hayUsuarioConectado()) {
            presenter.checkeoFavorito();
        }
    }

    @Override
    public void agregadoAFavoritos() {
        favorito.setText(getString(R.string.quitar_de_favoritos));
    }

    @Override
    public void quitadoDeFavoritos() {
        favorito.setText(getString(R.string.agregar_a_favoritos));
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finCargando() {
        progressBar.setVisibility(View.GONE);
    }



    private void setupListeners() {
        calificarBar.setOnClickListener(view -> {
            if (presenter.hayUsuarioConectado()) {
                DialogFragment comentarioDialog = new DialogComentario();
                comentarioDialog.show(getSupportFragmentManager(), "comentario");
            } else {
                toastShort(PaginaBarActivity.this, getString(R.string.necesitas_cuenta_calificar));
            }
        });

        favorito.setOnClickListener(v -> {
            if (presenter.hayUsuarioConectado()) {
                if (presenter.esFavorito()) {
                    presenter.quitarDeFavoritos();
                } else {
                    presenter.agregarAFavoritos();
                }

            } else {
                toastShort(PaginaBarActivity.this, getString(R.string.necesitas_cuenta_favoritos));
            }
        });
    }


    @Override
    public void enviarComentario(Comentario comentario) {
        presenter.enviarComentario(comentario);
        toastShort(this, getString(R.string.enviando));
    }

    @Override
    public void comentarioListo() {
        toastShort(this, getString(R.string.comentario_enviado));
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
