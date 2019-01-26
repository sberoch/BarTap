package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearDesafioContract;
import com.eriochrome.bartime.presenters.CrearDesafioPresenter;

public class CrearDesafioActivity extends AppCompatActivity implements CrearDesafioContract.View {

    private CrearDesafioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recordar meterle el layout correspondiente
        //Recordar agregar a manifest

        presenter = new CrearDesafioPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
