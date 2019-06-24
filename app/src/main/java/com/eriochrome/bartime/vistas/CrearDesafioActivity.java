package com.eriochrome.bartime.vistas;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearDesafioContract;
import com.eriochrome.bartime.presenters.CrearDesafioPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogDesafiosHint;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class CrearDesafioActivity extends AppCompatActivity implements CrearDesafioContract.View {

    private CrearDesafioPresenter presenter;
    private EditText desafioText;
    private Spinner spinnerDificultad;
    private CheckBox unicoGanador;
    private Button listo;
    private ArrayAdapter<String> spinnerAdapter;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_desafio);

        presenter = new CrearDesafioPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        desafioText = findViewById(R.id.desafio_text);
        spinnerDificultad = findViewById(R.id.dificultad_spinner);
        unicoGanador = findViewById(R.id.unico_ganador);
        listo = findViewById(R.id.listo);

        hint = findViewById(R.id.hint);
        hint.setTextColor(getResources().getColor(R.color.colorAccent));
        hint.setPaintFlags(hint.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        hint.setOnClickListener(v -> mostrarHintDesafios());
        listo.setOnClickListener(v -> presenter.enviarDesafio());

        setupSpinner();
    }


    @Override
    protected void onStart() {
        super.onStart();
        spinnerDificultad.setSelection(spinnerAdapter.getPosition(getString(R.string.medio)));
    }

    private void setupSpinner() {
        ArrayList<String> listaDificultades = new ArrayList<>();
        listaDificultades.add(getString(R.string.facil));
        listaDificultades.add(getString(R.string.medio));
        listaDificultades.add(getString(R.string.dificil));
        spinnerAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, listaDificultades);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinnerDificultad.setAdapter(spinnerAdapter);
    }

    @Override
    public String getDesafioText() {
        return desafioText.getText().toString();
    }

    @Override
    public String getDificultad() {
        return spinnerDificultad.getSelectedItem().toString();
    }

    @Override
    public void enviado() {
        toastShort(this, "Se ha enviado el desafio con exito!");
    }

    @Override
    public boolean esDeUnicoGanador() {
        return unicoGanador.isChecked();
    }

    private void mostrarHintDesafios() {
        DialogDesafiosHint dialogDesafiosHint = new DialogDesafiosHint();
        dialogDesafiosHint.show(getFragmentManager(), "desafiosHint");
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
