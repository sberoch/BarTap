package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.presenters.TriviaPresenter;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class TriviaActivity extends AppCompatActivity implements TriviaContract.View {

    //TODO: countdown
    //TODO: hacer con todas las preguntas en ciclo

    private TriviaPresenter presenter;

    private TextView pregunta;
    private Button opA;
    private Button opB;
    private Button opC;
    private View.OnClickListener opcionClickListener = v -> {
        String opcion = ((Button) v).getText().toString();
        if (presenter.eligioOpcionCorrecta(opcion)) {
            correcto();
        } else {
            incorrecto();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        presenter = new TriviaPresenter();
        presenter.bind(this);
        presenter.obtenerTrivia(getIntent());

        pregunta = findViewById(R.id.pregunta);
        opA = findViewById(R.id.op_a);
        opB = findViewById(R.id.op_b);
        opC = findViewById(R.id.op_c);

        opA.setOnClickListener(opcionClickListener);
        opB.setOnClickListener(opcionClickListener);
        opC.setOnClickListener(opcionClickListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.comenzarTrivia();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void llenar(String pregunta, String opA, String opB, String opC) {
        this.pregunta.setText(pregunta);
        this.opA.setText(opA);
        this.opB.setText(opB);
        this.opC.setText(opC);
    }


    private void incorrecto() {
        //TODO: poner en rojo
        //TODO: perder
        toastShort(this, "Incorrecto");
    }

    private void correcto() {
        //TODO: poner en verde
        //TODO: siguiente pregunta o terminar
        toastShort(this, "Correcto");
    }

    private void perdio() {
        //TODO: mostrar dialogo de salir (perdedor)
    }

    private void gano() {
        //TODO: actualizar puntos
        //TODO: ponerlo como ganador en la lista del bar
        //TODO: mostrar dialogo de salir (ganador)
    }
}
