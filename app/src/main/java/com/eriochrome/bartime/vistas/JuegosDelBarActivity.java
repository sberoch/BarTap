package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.JuegoDelBarHolder;
import com.eriochrome.bartime.adapters.JuegoHolder;
import com.eriochrome.bartime.adapters.ListaJuegosAdapter;
import com.eriochrome.bartime.contracts.JuegosDelBarContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.eriochrome.bartime.presenters.JuegosDelBarPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogCrearCuenta;
import com.eriochrome.bartime.vistas.dialogs.DialogResumenJuego;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class JuegosDelBarActivity extends AppCompatActivity implements
        JuegosDelBarContract.View,
        JuegoHolder.OnJuegoHolderClickListener,
        DialogResumenJuego.Listener,
        DialogCrearCuenta.Listener {

    private JuegosDelBarPresenter presenter;

    private RecyclerView juegosRecyclerView;
    private ListaJuegosAdapter juegosAdapter;
    private ProgressBar loading;
    private ImageButton volver;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos_del_bar);

        presenter = new JuegosDelBarPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        juegosRecyclerView = findViewById(R.id.recycler_view);
        juegosRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        juegosAdapter = new ListaJuegosAdapter(this, false);
        juegosRecyclerView.setAdapter(juegosAdapter);

        presenter.mostrarJuegos();
    }

    @Override
    public void cargando() {
        loading.setVisibility(View.VISIBLE);
        juegosAdapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Juego> juegos) {
        if (juegos.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        juegosAdapter.setItems(juegos);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void yaSeParticipo() {
        toastShort(this, getString(R.string.ya_participaste_juego));
    }

    @Override
    public void successParticipando() {
        toastShort(this, getString(R.string.exito_participar_juego));
    }

    @Override
    public void ingresarATrivia(Trivia trivia) {
        Intent i = new Intent(this, TriviaActivity.class);
        i.putExtra("trivia", trivia);
        startActivity(i);
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);

        juegosRecyclerView.setLayoutManager(layoutManager);
        juegosRecyclerView.addItemDecoration(espacioVerticalDecorator);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onClickJuego(Juego juego) {
        DialogResumenJuego dialog = new DialogResumenJuego();
        dialog.setJuego(juego);
        dialog.show(getFragmentManager(), "juego");
    }

    @Override
    public void intentarParticiparDeJuego(Juego juego) {
        if (presenter.estaConectado()) {
            presenter.intentarParticiparDeJuego(juego);
        } else {
            DialogCrearCuenta crearCuentaDialog = new DialogCrearCuenta();
            crearCuentaDialog.setTexto(getString(R.string.necesitas_cuenta_participar));
            crearCuentaDialog.show(getFragmentManager(), "crearCuentaDialog");
        }
    }

    @Override
    public void login() {
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.custom_login_ui)
                .setEmailButtonId(R.id.normal_login)
                .setGoogleButtonId(R.id.google_login)
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
}
