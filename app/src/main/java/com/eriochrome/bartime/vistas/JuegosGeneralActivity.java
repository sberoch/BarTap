package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.JuegoDelBarHolder;
import com.eriochrome.bartime.adapters.ListaJuegosAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.JuegosGeneralContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.presenters.JuegosGeneralPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class JuegosGeneralActivity extends AppCompatActivity implements JuegosGeneralContract.View, JuegoDelBarHolder.OnJuegoHolderClickListener {

    private JuegosGeneralPresenter presenter;

    private RecyclerView juegosRecyclerView;
    private ListaJuegosAdapter juegosAdapter;

    private Button crearJuego;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos_general);

        presenter = new JuegosGeneralPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        juegosRecyclerView = findViewById(R.id.recycler_view);
        juegosRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        juegosAdapter = new ListaJuegosAdapter(this, true);
        juegosRecyclerView.setAdapter(juegosAdapter);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        crearJuego = findViewById(R.id.crear_juego);
        crearJuego.setOnClickListener(v -> {
            Intent i = new Intent(JuegosGeneralActivity.this, NuevoJuegoActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        juegosAdapter.clear();
        presenter.mostrarJuegos();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        juegosAdapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Juego> listaJuegos) {
        if (listaJuegos.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        juegosAdapter.setItems(listaJuegos);
        progressBar.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        SombraEspacioVerticalDecorator sombra = new SombraEspacioVerticalDecorator(this, R.drawable.drop_shadow);

        juegosRecyclerView.setLayoutManager(layoutManager);
        juegosRecyclerView.addItemDecoration(espacioVerticalDecorator);
        juegosRecyclerView.addItemDecoration(sombra);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onClickJuego(Juego juego) {
        Intent i;
        if (presenter.esParticipable(juego)) {
            i = new Intent(this, PaginaJuegoParticipableActivity.class);
        } else {
            i = new Intent(this, PaginaTriviaActivity.class);
        }
        i = presenter.enviarJuego(i, juego);
        i = presenter.enviarBar(i);
        startActivity(i);
    }
}
