package com.eriochrome.bartime.vistas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.JuegoHolder;
import com.eriochrome.bartime.adapters.ListaJuegosAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.MisJuegosContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.presenters.MisJuegosPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogDejarDeParticipar;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class MisJuegosActivity extends AppCompatActivity implements
        MisJuegosContract.View,
        JuegoHolder.OnJuegoHolderClickListener,
        DialogDejarDeParticipar.Listener {

    private MisJuegosPresenter presenter;

    private ProgressBar progressBar;
    private ImageButton volver;

    private RecyclerView juegosRecyclerView;
    private ListaJuegosAdapter juegosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_juegos);

        presenter = new MisJuegosPresenter();
        presenter.bind(this);

        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        juegosRecyclerView = findViewById(R.id.recyclerView);
        juegosRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        juegosAdapter = new ListaJuegosAdapter(this, false);
        juegosRecyclerView.setAdapter(juegosAdapter);

        volver.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.mostrarJuegos();
    }

    @Override
    public void onClickJuego(Juego juego) {
        DialogDejarDeParticipar dialog = new DialogDejarDeParticipar();
        dialog.setJuego(juego);
        dialog.show(getFragmentManager(), "juego");
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
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        juegosAdapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Juego> juegos) {
        if (juegos.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        juegosAdapter.setItems(juegos);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void dejarDeParticipar(Juego juego) {
        presenter.dejarDeParticipar(juego);
        //hago esto porque sino no aparece borrado el juego al toque;
        finish();
    }
}
