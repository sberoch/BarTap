package com.eriochrome.bartime.vistas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.PaginaTriviaContract;
import com.eriochrome.bartime.presenters.PaginaTriviaPresenter;

public class PaginaTriviaActivity extends AppCompatActivity implements PaginaTriviaContract.View {

    private PaginaTriviaPresenter presenter;
    private ProgressBar progressBar;
    private ImageButton volver;

    private RelativeLayout container;
    private TextView tipoDeJuego;
    private TextView resumenDelJuego;
    private TextView cantGanadores;
    private TextView cantParticipantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_trivia);

        presenter = new PaginaTriviaPresenter();
        presenter.bind(this);
        presenter.obtenerJuego(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        container = findViewById(R.id.container_rl);
        tipoDeJuego = findViewById(R.id.tipo_de_juego);
        resumenDelJuego = findViewById(R.id.resumen_juego);
        cantGanadores = findViewById(R.id.cant_ganadores);
        cantParticipantes = findViewById(R.id.cant_participantes);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarDatosJuego();
        presenter.cargarDatosParticipantes();
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
    public void finCargando() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void setGanadores(int ganadores) {
        cantGanadores.setText(String.valueOf(ganadores));
    }

    @Override
    public void setParticipantes(int participantes) {
        cantParticipantes.setText(String.valueOf(participantes));
    }

    @Override
    public void setTipoDeJuego(String tipoDeJuego) {
        this.tipoDeJuego.setText(tipoDeJuego);
    }

    @Override
    public void setResumen(String resumen) {
        this.resumenDelJuego.setText(resumen);
    }
}
