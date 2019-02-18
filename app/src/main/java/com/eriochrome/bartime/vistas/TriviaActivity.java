package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.presenters.TriviaPresenter;

public class TriviaActivity extends AppCompatActivity implements TriviaContract.View {

    private TriviaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        presenter = new TriviaPresenter();
        presenter.bind(this);
        presenter.obtenerTrivia(getIntent());
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
