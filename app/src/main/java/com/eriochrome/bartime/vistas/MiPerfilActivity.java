package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.MiPerfilContract;
import com.eriochrome.bartime.presenters.MiPerfilPresenter;

public class MiPerfilActivity extends AppCompatActivity implements MiPerfilContract.View {

    private MiPerfilPresenter presenter;

    private Button juegos;
    private Button compras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        presenter = new MiPerfilPresenter();
        presenter.bind(this);

        juegos = findViewById(R.id.juegos);
        compras = findViewById(R.id.compras);

        juegos.setOnClickListener(v -> {
            startActivity(new Intent(MiPerfilActivity.this, MisJuegosActivity.class));
        });

        compras.setOnClickListener(v -> {
            //startActivity(new Intent(MiPerfilActivity.this, MisComprasActivity.class));
        });
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
