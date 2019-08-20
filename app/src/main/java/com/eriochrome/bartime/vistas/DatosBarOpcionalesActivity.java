package com.eriochrome.bartime.vistas;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarOpcionalesContract;
import com.eriochrome.bartime.presenters.DatosBarOpcionalesPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarOpcionalesActivity extends AppCompatActivity implements DatosBarOpcionalesContract.View {

    private DatosBarOpcionalesPresenter presenter;

    private CheckBox efectivo;
    private CheckBox tCredito;
    private CheckBox tDebito;
    private EditText telefono;
    private Button listo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_opcionales);

        presenter = new DatosBarOpcionalesPresenter();
        presenter.bind(this);

        efectivo = findViewById(R.id.efectivo);
        tCredito = findViewById(R.id.tarjeta_credito);
        tDebito = findViewById(R.id.tarjeta_debito);
        telefono = findViewById(R.id.telefono);
        listo = findViewById(R.id.listo);

        telefono.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) telefono.setHint("");
        });

        listo.setOnClickListener(v -> {
            presenter.setTelefono(getTelefono());
            presenter.setMetodosDePago(getMetodosDePago());
            presenter.subirBar();
        });

        presenter.obtenerBar(getIntent());
    }

    private String getTelefono() {
        return telefono.getText().toString();
    }

    private ArrayList<String> getMetodosDePago() {
        ArrayList<String> metodosDePago = new ArrayList<>();
        if (efectivo.isChecked()) {
            metodosDePago.add("efectivo");
        }
        if (tCredito.isChecked()) {
            metodosDePago.add("tarjeta de credito");
        }
        if (tDebito.isChecked()) {
            metodosDePago.add("tarjeta de debito");
        }
        return metodosDePago;
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void terminar() {
        if (!telefono.getText().toString().equals("")) {
            toastShort(DatosBarOpcionalesActivity.this, getResources().getString(R.string.exito));
            startActivity(new Intent(this, BarControlActivity.class));
        } else {
            toastShort(this, getString(R.string.debes_ingresar_telefono));
        }
    }

    @Override
    public void setMetodosDePago(ArrayList<String> metodosDePago) {
        if (metodosDePago.contains("efectivo")) {
            efectivo.setChecked(true);
        }
        if (metodosDePago.contains("tarjeta de credito")) {
            tCredito.setChecked(true);
        }
        if (metodosDePago.contains("tarjeta de debito")) {
            tDebito.setChecked(true);
        }
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono.setText(telefono);
    }
}
