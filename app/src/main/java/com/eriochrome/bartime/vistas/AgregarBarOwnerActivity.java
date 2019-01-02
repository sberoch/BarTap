package com.eriochrome.bartime.vistas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.AgregarBarOwnerContract;
import com.eriochrome.bartime.presenters.AgregarBarOwnerPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class AgregarBarOwnerActivity extends AppCompatActivity implements AgregarBarOwnerContract.View {

    private static final int NUMERO_SOLICITUD_GALERIA = 1;
    private Uri path;

    private AgregarBarOwnerPresenter presenter;

    private ProgressBar progressBar;
    private RelativeLayout agregarBarRl;

    private EditText nombreBar;
    private Button seleccionarFoto;
    private Button seleccionarUbicacion;
    private EditText horarioInicial;
    private EditText horarioFinal;
    private EditText happyhourInicial;
    private EditText happyhourFinal;
    private RadioButton efectivo;
    private RadioButton tCredito;
    private RadioButton tDebito;
    private Button listo;

    View.OnFocusChangeListener changeListener = (v, hasFocus) -> {
        if (!hasFocus) ocultarTeclado();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bar_owner);

        presenter = new AgregarBarOwnerPresenter();
        presenter.bind(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        agregarBarRl = findViewById(R.id.agregar_bar_rl);

        nombreBar = findViewById(R.id.nombre_bar_edit_text);
        seleccionarFoto = findViewById(R.id.seleccionar_imagen);
        seleccionarUbicacion = findViewById(R.id.seleccionar_ubicacion);
        horarioInicial = findViewById(R.id.horario_inicial);
        horarioFinal = findViewById(R.id.horario_final);
        happyhourInicial = findViewById(R.id.happyhour_inicial);
        happyhourFinal = findViewById(R.id.happyhour_final);
        efectivo = findViewById(R.id.efectivo);
        tCredito = findViewById(R.id.tarjeta_credito);
        tDebito = findViewById(R.id.tarjeta_debito);
        listo = findViewById(R.id.listo);

        setupListeners();

        //TODO: hacer preview de foto
        //TODO: preguntar si quiere reclamar un bar sin dueño
    }


    private void setupListeners() {
        listo.setOnClickListener(v -> presenter.listo());
        seleccionarFoto.setOnClickListener(v -> seleccionarImagenDeGaleria());
        //TODO: ubicacion
        nombreBar.setOnFocusChangeListener(changeListener);
        horarioInicial.setOnFocusChangeListener(changeListener);
        horarioFinal.setOnFocusChangeListener(changeListener);
        happyhourInicial.setOnFocusChangeListener(changeListener);
        happyhourFinal.setOnFocusChangeListener(changeListener);
    }


    @Override
    public String getTextNombreBar() {
        return nombreBar.getText().toString();
    }

    @Override
    public int getHorarioInicial() {
        return Integer.valueOf(horarioInicial.getText().toString());
    }

    @Override
    public int getHorarioFinal() {
        return Integer.valueOf(horarioFinal.getText().toString());
    }

    @Override
    public boolean tieneHappyHour() {
        //Tiene happy hour si ninguno de los dos campos esta vacio
        return ((!TextUtils.isEmpty(happyhourInicial.getText())) && (!TextUtils.isEmpty(happyhourFinal.getText())));
    }

    @Override
    public int getHappyhourInicial() {
        return Integer.valueOf(happyhourInicial.getText().toString());
    }

    @Override
    public int getHappyhourFinal() {
        return Integer.valueOf(happyhourFinal.getText().toString());
    }

    @Override
    public ArrayList<String> obtenerMetodosDePago() {
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


    private void seleccionarImagenDeGaleria() {
        Intent elegirFotoIntent = new Intent(Intent.ACTION_PICK);
        elegirFotoIntent.setType("image/*");
        startActivityForResult(elegirFotoIntent, NUMERO_SOLICITUD_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            path = data.getData();
            presenter.agregarFoto(path);
        }
        else {
            toastShort(AgregarBarOwnerActivity.this, "No elegiste ninguna imagen.");
        }
    }

    @Override
    public boolean hayImagen() {
        return path != null;
    }

    @Override
    public void noHayImagenError() {
        toastShort(AgregarBarOwnerActivity.this, "Debes elegir una imagen para continuar.");
    }

    @Override
    public void mostrarError() {
        toastShort(AgregarBarOwnerActivity.this, "Ocurrio un error inesperado. Intente nuevamente");
    }

    @Override
    public void subiendo() {
        progressBar.setVisibility(View.VISIBLE);
        agregarBarRl.setVisibility(View.GONE);
    }

    @Override
    public void finSubiendo() {
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(AgregarBarOwnerActivity.this, BarControlActivity.class));
        finish();
    }



    private void ocultarTeclado(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }


}
