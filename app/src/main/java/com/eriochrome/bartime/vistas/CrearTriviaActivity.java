package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearTriviaContract;
import com.eriochrome.bartime.presenters.CrearTriviaPresenter;

public class CrearTriviaActivity extends AppCompatActivity implements CrearTriviaContract.View {

    private CrearTriviaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_trivia);

        presenter = new CrearTriviaPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
