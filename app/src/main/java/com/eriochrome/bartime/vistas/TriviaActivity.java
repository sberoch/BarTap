package com.eriochrome.bartime.vistas;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.TriviaContract;
import com.eriochrome.bartime.presenters.TriviaPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogTerminoTrivia;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class TriviaActivity extends AppCompatActivity implements TriviaContract.View {

    private final static int TIMER_MAX = 10000; //10 segs

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
            perdio();
        }
    };

    private TextView timerTextView;
    private CountDownTimer cdtimer = new CountDownTimer(TIMER_MAX, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timerTextView.setText(String.valueOf(millisUntilFinished / 1000));
        }

        @Override
        public void onFinish() {
            perdio();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        presenter = new TriviaPresenter();
        presenter.bind(this);
        presenter.obtenerTrivia(getIntent());

        timerTextView = findViewById(R.id.countdown);

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
        presenter.cargarSiguientePregunta();
        cdtimer.start();
    }


    @Override
    public void llenar(String pregunta, String opA, String opB, String opC) {
        this.pregunta.setText(pregunta);
        this.opA.setText(opA);
        this.opB.setText(opB);
        this.opC.setText(opC);
    }

    private void correcto() {
        toastShort(this, getString(R.string.correcto));
        if (presenter.quedanPreguntas()) {
            presenter.cargarSiguientePregunta();
            cdtimer.start();
        } else {
            gano();
        }
    }

    private void perdio() {
        DialogTerminoTrivia dialogTerminoTrivia = new DialogTerminoTrivia();
        dialogTerminoTrivia.setTexto(getString(R.string.texto_perdedor_trivia));
        dialogTerminoTrivia.show(getFragmentManager(), "terminoTrivia");
    }

    private void gano() {
        cdtimer.cancel();

        presenter.actualizarPuntos();
        presenter.agregarGanador();

        DialogTerminoTrivia dialogTerminoTrivia = new DialogTerminoTrivia();
        dialogTerminoTrivia.setTexto(getString(R.string.texto_ganador_trivia));
        dialogTerminoTrivia.show(getFragmentManager(), "terminoTrivia");
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        if (cdtimer != null)
            cdtimer.cancel();
        super.onDestroy();
    }
}
