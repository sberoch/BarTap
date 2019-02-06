package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.CrearTriviaContract;
import com.eriochrome.bartime.presenters.CrearTriviaPresenter;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class CrearTriviaActivity extends AppCompatActivity implements CrearTriviaContract.View {

    private CrearTriviaPresenter presenter;

    private EditText titulo;
    private EditText cantPreguntas;
    private Button continuar;

    private String textoError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_trivia);

        presenter = new CrearTriviaPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        titulo = findViewById(R.id.titulo);
        cantPreguntas = findViewById(R.id.cant_preguntas);
        continuar = findViewById(R.id.continuar);

        continuar.setOnClickListener(v -> {
            if (textosValidos()) {
                presenter.comenzarCreacionTrivia();
                Intent i = new Intent(CrearTriviaActivity.this, CrearPreguntasTriviaActivity.class);
                i = presenter.enviarBar(i);
                i = presenter.enviarTrivia(i);
                startActivity(i);
            } else {
                mostrarError(textoError);
            }
        });

    }

    @Override
    public String getTitulo() {
        return titulo.getText().toString();
    }

    @Override
    public int getCantPreguntas() {
        return Integer.valueOf(cantPreguntas.getText().toString());
    }

    private boolean textosValidos() {
        boolean valido = true;
        if (titulo.getText().toString().equals("")) {
            valido = false;
            textoError = getString(R.string.debe_ingresar_titulo_trivia);
        }
        if (cantPreguntas.getText().toString().equals("")) {
            valido = false;
            textoError = getString(R.string.debe_ingresar_cant_preguntas_trivia);
        }
        int cant = 5;
        try {
            cant = Integer.valueOf(cantPreguntas.getText().toString());
        } catch (NumberFormatException e) {
            valido = false;
            textoError = getString(R.string.debe_ingresar_numero_preguntas_trivia);
        }
        if (cant < 1 || cant > 10) {
            valido = false;
            textoError = getString(R.string.out_of_bounds_cant_preguntas_trivia);
        }
        return valido;
    }

    private void mostrarError(String textoError) {
        toastShort(this, textoError);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
