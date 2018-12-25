package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.PaginaBarContract;
import com.eriochrome.bartime.presenters.PaginaBarPresenter;

public class PaginaBarActivity extends AppCompatActivity implements PaginaBarContract.View {

    private TextView nombreBar;
    private Button calificacionOk;
    private EditText calificacionEditText;

    private PaginaBarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        presenter = new PaginaBarPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        //TODO: hacer opcion agregar a favoritos

        nombreBar = findViewById(R.id.nombre_bar);
        calificacionEditText = findViewById(R.id.calificacion);
        calificacionOk = findViewById(R.id.calificacion_ok);

        nombreBar.setText(presenter.getNombreDeBar());
        calificacionOk.setOnClickListener(view -> {
            presenter.calificarBar();
           finish();
        });
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public int getCalificacion() {
        return Integer.parseInt(calificacionEditText.getText().toString());
    }
}
