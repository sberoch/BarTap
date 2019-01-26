package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.NuevoJuegoContract;
import com.eriochrome.bartime.presenters.NuevoJuegoPresenter;

import java.util.ArrayList;

public class NuevoJuegoActivity extends AppCompatActivity implements NuevoJuegoContract.View {

    private NuevoJuegoPresenter presenter;

    private Button crearDesafio;
    private Button crearTrivia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_juego);

        presenter = new NuevoJuegoPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        crearDesafio = findViewById(R.id.crear_desafio);
        crearTrivia = findViewById(R.id.crear_trivia);

        crearDesafio.setOnClickListener(v -> {
            Intent i = new Intent(NuevoJuegoActivity.this, CrearDesafioActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });

        crearTrivia.setOnClickListener(v -> {
            //TODO: crear trivia activity
        });
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
