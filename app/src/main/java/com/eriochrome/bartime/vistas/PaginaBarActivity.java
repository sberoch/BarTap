package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.ListaComentariosAdapter;
import com.eriochrome.bartime.adapters.ViewPagerAdapter;
import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.modelos.Comentario;
import com.eriochrome.bartime.presenters.PaginaBarPresenter;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class PaginaBarActivity extends AppCompatActivity implements PaginaBarContract.View, DialogComentario.ComentarioListener, DialogCrearCuenta.Listener {

    private static final int RC_SIGN_IN = 1;
    private RelativeLayout paginaBarRl;
    private TextView nombreBar;
    private Button calificarBar;
    private Button favorito;
    private ListView listView;
    private Button verMas;
    private Button verMapa;

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private ListaComentariosAdapter listaComentariosAdapter;

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

        paginaBarRl = findViewById(R.id.pagina_bar_rl);
        nombreBar = findViewById(R.id.nombre_bar);
        calificarBar = findViewById(R.id.calificarBar);
        favorito = findViewById(R.id.agregar_favorito);
        verMas = findViewById(R.id.ver_mas);
        verMapa = findViewById(R.id.ver_mapa);

        viewPager = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this, presenter.getBar());
        viewPager.setAdapter(viewPagerAdapter);

        listView = findViewById(R.id.listview);
        listaComentariosAdapter = new ListaComentariosAdapter();
        listView.setAdapter(listaComentariosAdapter);

        nombreBar.setText(presenter.getNombreDeBar());
        setupListeners();

        //TODO: se muestra de a un comentario, esta medio bug que repite dentro de la lista y no se porque
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter.hayUsuarioConectado()) {
            presenter.checkeoFavorito();
            presenter.checkearUsuarioCalificoBar();
        }
        presenter.cargarComentarios();
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
        paginaBarRl.setVisibility(View.GONE);
    }

    @Override
    public void finCargando() {
        progressBar.setVisibility(View.GONE);
        paginaBarRl.setVisibility(View.VISIBLE);
    }


    private void setupListeners() {
        calificarBar.setOnClickListener(view -> {
            if (presenter.hayUsuarioConectado()) {
                DialogFragment comentarioDialog = new DialogComentario();
                comentarioDialog.show(getSupportFragmentManager(), "comentario");
            } else {
                DialogCrearCuenta crearCuentaDialog = new DialogCrearCuenta();
                crearCuentaDialog.setTexto(getString(R.string.necesitas_cuenta_calificar));
                crearCuentaDialog.show(getFragmentManager(), "crearCuentaDialog");
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
                DialogCrearCuenta crearCuentaDialog = new DialogCrearCuenta();
                crearCuentaDialog.setTexto(getString(R.string.necesitas_cuenta_favoritos));
                crearCuentaDialog.show(getFragmentManager(), "crearCuentaDialog");
            }
        });

        verMas.setOnClickListener(v -> {
            Intent i = new Intent(PaginaBarActivity.this, ComentariosActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });

        verMapa.setOnClickListener(v -> {
            Intent i = new Intent(PaginaBarActivity.this, VerMapaActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
    }


    @Override
    public void login() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.drawable.bar_time_2)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                overridePendingTransition(0,0);
                finish();
            }
        }
    }

    @Override
    public void enviarComentario(Comentario comentario) {
        presenter.enviarComentario(comentario);
        toastShort(this, getString(R.string.enviando));
    }

    @Override
    public void comentarioListo() {
        toastShort(this, getString(R.string.comentario_enviado));
        calificarBar.setVisibility(View.GONE);
    }

    @Override
    public void yaCalificoElBar() {
        calificarBar.setVisibility(View.GONE);
    }

    @Override
    public void cargaDeComentarios() {
        listaComentariosAdapter.clear();
    }

    @Override
    public void finCargaDeComentarios() {
        listaComentariosAdapter.setItems(presenter.getComentarios());
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

}
