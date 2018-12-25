package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.LauncherContract;
import com.eriochrome.bartime.presenters.LauncherPresenter;


public class LauncherActivity extends AppCompatActivity implements LauncherContract.View {

    private LauncherPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        presenter = new LauncherPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.redirigir();
    }

    @Override
    public void startNuevo() {
        startActivity(new Intent(this, DistincionDeUsuarioActivity.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void startUsuario() {
        startActivity(new Intent(LauncherActivity.this, ListadosActivity.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    public void startBar() {
        startActivity(new Intent(LauncherActivity.this, BarControlActivity.class));
        overridePendingTransition(0,0);
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
