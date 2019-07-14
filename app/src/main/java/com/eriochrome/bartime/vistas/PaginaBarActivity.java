package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.ListaComentariosAdapter;
import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.modelos.entidades.Comentario;
import com.eriochrome.bartime.presenters.PaginaBarPresenter;
import com.eriochrome.bartime.utils.MySliderView;
import com.eriochrome.bartime.vistas.dialogs.DialogComentario;
import com.eriochrome.bartime.vistas.dialogs.DialogCrearCuenta;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class PaginaBarActivity extends AppCompatActivity implements PaginaBarContract.View, DialogComentario.ComentarioListener, DialogCrearCuenta.Listener {

    private static final int RC_SIGN_IN = 1;
    private RelativeLayout paginaBarRl;
    private TextView nombreBar;
    private TextView descripcion;
    private TextView ubicacion;
    private Button calificarBar;
    private ImageButton favorito;
    private ListView listView;
    private Button verMas;
    private TextView puntosText;
    private Button tienda;
    private Button juegos;

    private SliderLayout sliderShow;

    private ListaComentariosAdapter listaComentariosAdapter;

    private ProgressBar progressBar;
    private ImageButton volver;

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
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        paginaBarRl = findViewById(R.id.pagina_bar_rl);
        nombreBar = findViewById(R.id.nombre_bar);
        descripcion = findViewById(R.id.descripcion);
        ubicacion = findViewById(R.id.ubicacion);
        calificarBar = findViewById(R.id.calificarBar);
        favorito = findViewById(R.id.favorito);
        verMas = findViewById(R.id.ver_mas);
        puntosText = findViewById(R.id.puntos_text);
        tienda = findViewById(R.id.tienda);
        juegos = findViewById(R.id.juegos);
        sliderShow = findViewById(R.id.slider);

        listView = findViewById(R.id.listview);
        listaComentariosAdapter = new ListaComentariosAdapter();
        listView.setAdapter(listaComentariosAdapter);
        listaComentariosAdapter.notifyDataSetChanged();

        nombreBar.setText(presenter.getNombreDeBar());
        setupDescripcion();
        ubicacion.setText(presenter.getUbicacionDeBar());
        puntosText.setVisibility(View.INVISIBLE);
        setupListeners();

        presenter.cargarComentarios();
        presenter.cargarImagenes();
    }

    private void setupDescripcion() {
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        descripcion.setTypeface(tf);
        String desc = presenter.getDescripcion();
        if (!desc.equals(""))
            descripcion.setText(desc);
        else
            descripcion.setText(getString(R.string.aun_sin_descripcion));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter.hayUsuarioConectado()) {
            presenter.cargarPuntosEnElBar();
            presenter.checkeoFavorito();
            presenter.checkearUsuarioCalificoBar();
        }
        listaComentariosAdapter.notifyDataSetChanged();
    }

    @Override
    public void agregadoAFavoritos() {
        favorito.setImageResource(R.drawable.ic_favorite_24dp);
    }

    @Override
    public void quitadoDeFavoritos() {
        favorito.setImageResource(R.drawable.ic_favorite_border_violet_24dp);
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

        ubicacion.setOnClickListener(v -> {
            Intent i = new Intent(PaginaBarActivity.this, VerMapaActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });

        tienda.setOnClickListener(v -> {
            if (presenter.hayUsuarioConectado()) {
                Intent i = new Intent(PaginaBarActivity.this, TiendaActivity.class);
                i = presenter.enviarBar(i);
                startActivity(i);
            } else {
                DialogCrearCuenta dialogCrearCuenta = new DialogCrearCuenta();
                dialogCrearCuenta.setTexto(getString(R.string.necesitas_cuenta_tienda));
                dialogCrearCuenta.show(getFragmentManager(), "crearCuentaDialog");
            }
        });

        juegos.setOnClickListener(v -> {
            Intent i = new Intent(PaginaBarActivity.this, JuegosDelBarActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
    }


    @Override
    public void login() {
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.custom_login_ui)
                .setGoogleButtonId(R.id.google_login)
                .setEmailButtonId(R.id.normal_login)
                .build();

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAuthMethodPickerLayout(customLayout)
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
        listaComentariosAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPuntos(Integer puntos) {
        String texto = "(" + Integer.toString(puntos) + " puntos)";
        puntosText.setText(texto);
        puntosText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onImageLoaded(String path) {
        MySliderView sliderView = new MySliderView(this);
        sliderView.image(path)
                .setScaleType(BaseSliderView.ScaleType.CenterInside);
        sliderShow.addSlider(sliderView);
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

}
