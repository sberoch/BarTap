package com.eriochrome.bartime.vistas;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.ConfirmarNuevoBarContract;
import com.eriochrome.bartime.presenters.ConfirmarNuevoBarPresenter;

public class ConfirmarNuevoBarActivity extends AppCompatActivity implements ConfirmarNuevoBarContract.View {

    private ConfirmarNuevoBarPresenter presenter;

    private Button continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_nuevo_bar);

        presenter = new ConfirmarNuevoBarPresenter();
        presenter.bind(this);

        continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> {
            startActivity(new Intent(ConfirmarNuevoBarActivity.this, ListadosActivity.class));
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
