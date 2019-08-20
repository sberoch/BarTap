package com.eriochrome.bartime.vistas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearSorteoContract;
import com.eriochrome.bartime.presenters.CrearSorteoPresenter;
import com.eriochrome.bartime.utils.DateFormatter;

import java.util.Calendar;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class CrearSorteoActivity extends AppCompatActivity implements
        CrearSorteoContract.View,
        DatePickerDialog.OnDateSetListener {

    private CrearSorteoPresenter presenter;

    private Button fecha;
    private EditText puntos;
    private Button continuar;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_sorteo);

        presenter = new CrearSorteoPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        fecha = findViewById(R.id.fecha);
        puntos = findViewById(R.id.puntos);
        continuar = findViewById(R.id.continuar);

        fecha.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, CrearSorteoActivity.this,
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        continuar.setOnClickListener(v -> {
            if (datosCompletos()) {
                presenter.enviarSorteo();
            }
        });

    }

    private boolean datosCompletos() {
        boolean completos = true;
        if (fecha.getText().toString().equals(getString(R.string.placeholder_fecha))) {
            completos = false;
            toastShort(this, getString(R.string.debes_elegir_fecha));
        }
        if (puntos.getText().toString().equals("")) {
            completos = false;
            toastShort(this, getString(R.string.debes_recompensa_ganador));
        }
        return completos;
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fechaText = DateFormatter.toString(dayOfMonth, month, year);
        fecha.setText(fechaText);
    }

    @Override
    public String getFechaFin() {
        return fecha.getText().toString();
    }

    @Override
    public String getPuntos() {
        return puntos.getText().toString();
    }

    @Override
    public void enviado() {
        toastShort(this, getString(R.string.sorteo_enviado_exito));
        finish();
    }
}
