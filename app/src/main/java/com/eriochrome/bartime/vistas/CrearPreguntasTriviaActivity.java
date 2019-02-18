package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearPreguntasTriviaContract;
import com.eriochrome.bartime.presenters.CrearPreguntasTriviaPresenter;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class CrearPreguntasTriviaActivity extends AppCompatActivity implements CrearPreguntasTriviaContract.View {

    private CrearPreguntasTriviaPresenter presenter;

    private EditText pregunta;
    private EditText opcionA;
    private EditText opcionB;
    private EditText opcionC;
    private RadioGroup radioGroup;

    private Button siguiente;
    private Button listo;

    private String textoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_preguntas_trivia);

        presenter = new CrearPreguntasTriviaPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());
        presenter.obtenerTrivia(getIntent());

        pregunta = findViewById(R.id.pregunta);
        opcionA = findViewById(R.id.opcionA);
        opcionB = findViewById(R.id.opcionB);
        opcionC = findViewById(R.id.opcionC);
        radioGroup = findViewById(R.id.radioGroup);
        siguiente = findViewById(R.id.siguiente);
        listo = findViewById(R.id.listo);
        listo.setVisibility(View.GONE);

        siguiente.setOnClickListener(v -> {
            if (camposValidos()) {
                presenter.guardarPregunta();
                borrarCampos();
                presenter.checkearPreguntasRestantes();
            } else {
                mostrarError(textoError);
            }
        });

        listo.setOnClickListener(v -> {
            if (camposValidos()) {
                presenter.guardarPregunta();
                presenter.subirTrivia();
                toastShort(CrearPreguntasTriviaActivity.this, getString(R.string.enviando_trivia));
                startActivity(new Intent(CrearPreguntasTriviaActivity.this, BarControlActivity.class));
                finish();
            } else {
                mostrarError(textoError);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.checkearPreguntasRestantes();
    }

    @Override
    public void ultimaPregunta() {
        siguiente.setVisibility(View.GONE);
        listo.setVisibility(View.VISIBLE);
    }

    @Override
    public String getPregunta() {
        return pregunta.getText().toString();
    }

    @Override
    public String getOpcionA() {
        return opcionA.getText().toString();
    }

    @Override
    public String getOpcionB() {
        return opcionB.getText().toString();
    }

    @Override
    public String getOpcionC() {
        return opcionC.getText().toString();
    }

    @Override
    public String getRespuestaCorrecta() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.op_a_correcta:
                return "A";
            case R.id.op_b_correcta:
                return "B";
            case R.id.op_c_correcta:
                return "C";
            default:
                return "";
        }
    }

    @Override
    public void enviado() {
        toastShort(this, "La trivia fue enviada con exito.");
    }

    private boolean camposValidos() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            textoError = getString(R.string.debe_seleccionar_opcion_correcta);
            return false;
        }
        if (pregunta.getText().toString().equals("")) {
            textoError = getString(R.string.no_debe_dejar_campos_vacios);
            return false;
        }
        if (opcionA.getText().toString().equals("")) {
            textoError = getString(R.string.no_debe_dejar_campos_vacios);
            return false;
        }
        if (opcionB.getText().toString().equals("")) {
            textoError = getString(R.string.no_debe_dejar_campos_vacios);
            return false;
        }
        if (opcionC.getText().toString().equals("")) {
            textoError = getString(R.string.no_debe_dejar_campos_vacios);
            return false;
        }
        return true;
    }

    private void mostrarError(String textoError) {
        toastShort(this, textoError);
    }

    private void borrarCampos() {
        pregunta.setText("");
        opcionA.setText("");
        opcionB.setText("");
        opcionC.setText("");
        radioGroup.clearCheck();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }


}
