package com.eriochrome.bartime.vistas;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarOpcionalesContract;
import com.eriochrome.bartime.presenters.DatosBarOpcionalesPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarOpcionalesActivity extends AppCompatActivity implements DatosBarOpcionalesContract.View {

    //TODO: subir a database
    //TODO: callback de cuando se termine de subir volver a barcontrol

    private DatosBarOpcionalesPresenter presenter;

    private CheckBox efectivo;
    private CheckBox tCredito;
    private CheckBox tDebito;
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
        listo = findViewById(R.id.listo);

        listo.setOnClickListener(v -> {
            presenter.setMetodosDePago(getMetodosDePago());
            presenter.subirBar();
        });

        presenter.obtenerBar(getIntent());
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
        toastShort(DatosBarOpcionalesActivity.this, getResources().getString(R.string.exito));
        startActivity(new Intent(this, BarControlActivity.class));
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
}
