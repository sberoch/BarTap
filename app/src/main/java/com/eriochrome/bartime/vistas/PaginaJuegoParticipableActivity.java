package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.PaginaJuegoParticipableContract;
import com.eriochrome.bartime.presenters.PaginaJuegoParticipablePresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogValidarGanador;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class PaginaJuegoParticipableActivity extends AppCompatActivity implements PaginaJuegoParticipableContract.View, DialogValidarGanador.Listener {

    private PaginaJuegoParticipablePresenter presenter;

    private ProgressBar progressBar;
    private ImageButton volver;

    private TextView tipoDeJuego;
    private TextView resumenDelJuego;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String posibleGanador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_juego_participable);

        presenter = new PaginaJuegoParticipablePresenter();
        presenter.bind(this);
        presenter.obtenerJuego(getIntent());
        presenter.obtenerBar(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        tipoDeJuego = findViewById(R.id.tipo_de_juego);
        resumenDelJuego = findViewById(R.id.resumen_juego);
        listView = findViewById(R.id.listView);

        }

    @Override
    protected void onStart() {
        super.onStart();
        tipoDeJuego.setText(presenter.getTipoDeJuego());
        resumenDelJuego.setText(presenter.getResumenJuego());
        presenter.setupAdapter();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finCargando(ArrayList<String> participantes) {
        if (participantes.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        setupAdapter(participantes);
        progressBar.setVisibility(View.GONE);
    }

    private void setupAdapter(ArrayList<String> participantes) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, participantes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            presenter.onClickParticipante();
            posibleGanador = adapter.getItem(position);
        });
    }

    @Override
    public void abrirDialogValidarGanador() {
        DialogValidarGanador dialogValidarGanador = new DialogValidarGanador();
        dialogValidarGanador.show(getFragmentManager(), "validarGanador");
    }

    @Override
    public void declararGanador() {
        presenter.declararGanador(posibleGanador);
        startActivity(new Intent(this, BarControlActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
