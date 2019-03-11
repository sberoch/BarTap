package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.presenters.TriviaPresenter;

public class TriviaActivity extends AppCompatActivity implements TriviaContract.View {

    private TriviaPresenter presenter;

    private ProgressBar progressBar;
    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        presenter = new TriviaPresenter();
        presenter.bind(this);
        presenter.obtenerTrivia(getIntent());

        progressBar = findViewById(R.id.progressBar);
        rl = findViewById(R.id.trivia_rl);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.comenzarTrivia();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        rl.setVisibility(View.GONE);
    }

    @Override
    public void finCargando() {
        progressBar.setVisibility(View.GONE);
        rl.setVisibility(View.VISIBLE);
    }
}
