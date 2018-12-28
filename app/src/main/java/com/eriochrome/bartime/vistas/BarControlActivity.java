package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.AgregarBarOwnerContract;
import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.presenters.BarControlPresenter;

public class BarControlActivity extends AppCompatActivity implements BarControlContract.View {

    private BarControlPresenter presenter;

    private ProgressBar loading;
    private RelativeLayout sinBarRl;
    private Button sinBarButton;

    private RelativeLayout barControlRl;
    private TextView nombreBar;
    private ImageButton editarBar;
    private Button crearDesafio;
    private Button crearOferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_control);

        presenter = new BarControlPresenter();
        presenter.bind(this);

        loading = findViewById(R.id.progressBar);

        sinBarRl = findViewById(R.id.sin_bar_rl);
        sinBarButton = findViewById(R.id.sin_bar_btn);

        barControlRl = findViewById(R.id.bar_control_rl);
        nombreBar = findViewById(R.id.nombre_bar);
        editarBar = findViewById(R.id.editar_bar);
        crearDesafio = findViewById(R.id.crear_desafio);
        crearOferta = findViewById(R.id.crear_oferta);

        setupListeners();
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.setupBar();
    }

    private void updateUI() {
        if (presenter.hayBarAsociado()) {
            sinBarRl.setVisibility(View.GONE);
            barControlRl.setVisibility(View.VISIBLE);
            nombreBar.setText(presenter.getNombreBar());
        } else {
            sinBarRl.setVisibility(View.VISIBLE);
            barControlRl.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    private void setupListeners() {
        sinBarButton.setOnClickListener(v -> {
            startActivity(new Intent(BarControlActivity.this, AgregarBarOwnerActivity.class));
            finish();
        });
        editarBar.setOnClickListener(v -> {
            presenter.editarBar();
        });
        crearDesafio.setOnClickListener(v -> {
            presenter.crearDesafio();
        });
        crearOferta.setOnClickListener(v -> {
            presenter.crearOferta();
        });
    }

    @Override
    public void cargando() {
        sinBarRl.setVisibility(View.GONE);
        barControlRl.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void finCargando() {
        loading.setVisibility(View.GONE);
        updateUI();
    }
}
