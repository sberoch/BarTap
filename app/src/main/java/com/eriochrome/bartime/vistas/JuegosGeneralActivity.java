package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.JuegoDelBarHolder;
import com.eriochrome.bartime.adapters.ListaJuegosAdapter;
import com.eriochrome.bartime.contracts.JuegosGeneralContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.presenters.JuegosGeneralPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogCrearJuego;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class JuegosGeneralActivity extends AppCompatActivity implements
        JuegosGeneralContract.View,
        JuegoDelBarHolder.OnJuegoHolderClickListener,
        DialogCrearJuego.OnButtonClick {

    private JuegosGeneralPresenter presenter;

    private RecyclerView juegosRecyclerView;
    private ListaJuegosAdapter juegosAdapter;

    private Button crearJuego;

    private ProgressBar progressBar;
    private ImageButton volver;

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
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        crearJuego = findViewById(R.id.crear_juego);
        crearJuego.setOnClickListener(v -> {
            DialogCrearJuego dialogCrearJuego = new DialogCrearJuego();
            dialogCrearJuego.show(getFragmentManager(), "crearJuego");
        });

        presenter.mostrarJuegos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        juegosAdapter.notifyDataSetChanged();
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
        Intent i;
        if (presenter.esSorteo(juego)) {
            i = new Intent(this, PaginaSorteoActivity.class);
        } else if (presenter.esTrivia(juego)) {
            i = new Intent(this, PaginaTriviaActivity.class);
        } else {
            i = new Intent(this, PaginaJuegoParticipableActivity.class);
        }
        i = presenter.enviarJuego(i, juego);
        i = presenter.enviarBar(i);
        startActivity(i);
    }

    @Override
    public void crearJuegoConTipo(String tipoDeJuego) {
        Intent i = null;
        switch (tipoDeJuego) {
            case "Desafio":
                i = new Intent(JuegosGeneralActivity.this, CrearDesafioActivity.class);
                break;
            case "Trivia":
                i = new Intent(JuegosGeneralActivity.this, CrearTriviaActivity.class);
                break;
            case "Sorteo":
                i = new Intent(JuegosGeneralActivity.this, CrearSorteoActivity.class);
                break;
            default:
                toastShort(this, "Ocurrio un error inesperado.");
                finish();
        }

        if (i != null) {
            i = presenter.enviarBar(i);
            startActivity(i);
            finish();
        }
    }
}
