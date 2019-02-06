package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearPreguntasTriviaContract;
import com.eriochrome.bartime.presenters.CrearPreguntasTriviaPresenter;

public class CrearPreguntasTriviaActivity extends AppCompatActivity implements CrearPreguntasTriviaContract.View {

    private CrearPreguntasTriviaPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recordar meterle el layout correspondiente
        //Recordar agregar a manifest

        presenter = new CrearPreguntasTriviaPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
