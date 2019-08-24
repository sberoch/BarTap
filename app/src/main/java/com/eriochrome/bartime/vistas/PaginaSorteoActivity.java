package com.eriochrome.bartime.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.PaginaSorteoContract;
import com.eriochrome.bartime.presenters.PaginaSorteoPresenter;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class PaginaSorteoActivity extends AppCompatActivity implements PaginaSorteoContract.View {

    private PaginaSorteoPresenter presenter;
    private ProgressBar progressBar;
    private ImageButton volver;

    private RelativeLayout container;
    private TextView resumenDelJuego;
    private TextView cantParticipantes;
    private Button sortear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_sorteo);

        presenter = new PaginaSorteoPresenter();
        presenter.bind(this);
        presenter.obtenerJuego(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        container = findViewById(R.id.container_rl);
        resumenDelJuego = findViewById(R.id.resumen_juego);
        cantParticipantes = findViewById(R.id.cant_participantes);
        sortear = findViewById(R.id.sortear);
        sortear.setOnClickListener(v -> {
            if (!cantParticipantes.getText().equals("0")) {
                presenter.sortear();
            } else {
                toastShort(PaginaSorteoActivity.this, getString(R.string.no_sortear_sin_participantes));
            }
        });

        presenter.cargarDatos();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }

    @Override
    public void finSorteo(String participanteGanador) {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        Intent i = new Intent(PaginaSorteoActivity.this, FinSorteoActivity.class);
        i.putExtra("ganador", participanteGanador);
        startActivity(i);
        finish();
    }

    @Override
    public void setResumenJuego(String resumenJuego) {
        resumenDelJuego.setText(resumenJuego);
    }

    @Override
    public void setCantParticipantes(int cantParticipantes) {
        this.cantParticipantes.setText(String.valueOf(cantParticipantes));
    }

    @Override
    public void finCargando() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }
}
