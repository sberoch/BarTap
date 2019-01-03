package com.eriochrome.bartime.vistas;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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
import com.eriochrome.bartime.utils.TimePicker;

import java.util.ArrayList;
import java.util.HashMap;

import static com.eriochrome.bartime.utils.Utils.editTextToInt;
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

    /**
     * hi: Horario Inicial
     * hf: Horario Final
     * hhi: HappyHour Inicial
     * hhf: HappyHour Final
     */

    private EditText hiLunes, hfLunes;
    private EditText hiMartes, hfMartes;
    private EditText hiMiercoles, hfMiercoles;
    private EditText hiJueves, hfJueves;
    private EditText hiViernes, hfViernes;
    private EditText hiSabado, hfSabado;
    private EditText hiDomingo, hfDomingo;

    private EditText hhiLunes, hhfLunes;
    private EditText hhiMartes, hhfMartes;
    private EditText hhiMiercoles, hhfMiercoles;
    private EditText hhiJueves, hhfJueves;
    private EditText hhiViernes, hhfViernes;
    private EditText hhiSabado, hhfSabado;
    private EditText hhiDomingo, hhfDomingo;

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
        setupHorarios();
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
    }


    @Override
    public String getTextNombreBar() {
        return nombreBar.getText().toString();
    }

    @Override
    public HashMap<String, Integer> getHorariosInicial() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo",editTextToInt(hiDomingo));
        devolver.put("Lunes",editTextToInt(hiLunes));
        devolver.put("Martes",editTextToInt(hiMartes));
        devolver.put("Miercoles",editTextToInt(hiMiercoles));
        devolver.put("Jueves",editTextToInt(hiJueves));
        devolver.put("Viernes",editTextToInt(hiViernes));
        devolver.put("Sabado",editTextToInt(hiSabado));
        return devolver;
    }

    @Override
    public HashMap<String, Integer> getHorariosFinal() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo",editTextToInt(hfDomingo));
        devolver.put("Lunes",editTextToInt(hfLunes));
        devolver.put("Martes",editTextToInt(hfMartes));
        devolver.put("Miercoles",editTextToInt(hfMiercoles));
        devolver.put("Jueves",editTextToInt(hfJueves));
        devolver.put("Viernes",editTextToInt(hfViernes));
        devolver.put("Sabado",editTextToInt(hfSabado));
        return devolver;
    }


    //TODO: Hacer alguna forma mas piola de preguntar esto
    @Override
    public boolean tieneHappyHour() {
        return ((!TextUtils.isEmpty(hhiSabado.getText())) && (!TextUtils.isEmpty(hhfSabado.getText())));
    }

    @Override
    public HashMap<String, Integer> getHappyhourInicial() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo",editTextToInt(hhiDomingo));
        devolver.put("Lunes",editTextToInt(hhiLunes));
        devolver.put("Martes",editTextToInt(hhiMartes));
        devolver.put("Miercoles",editTextToInt(hhiMiercoles));
        devolver.put("Jueves",editTextToInt(hhiJueves));
        devolver.put("Viernes",editTextToInt(hhiViernes));
        devolver.put("Sabado",editTextToInt(hhiSabado));
        return devolver;
    }

    @Override
    public HashMap<String, Integer> getHappyhourFinal() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo",editTextToInt(hhfDomingo));
        devolver.put("Lunes",editTextToInt(hhfLunes));
        devolver.put("Martes",editTextToInt(hhfMartes));
        devolver.put("Miercoles",editTextToInt(hhfMiercoles));
        devolver.put("Jueves",editTextToInt(hhfJueves));
        devolver.put("Viernes",editTextToInt(hhfViernes));
        devolver.put("Sabado",editTextToInt(hhfSabado));
        return devolver;

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


    //TODO: muestra minutos :c
    private void setupHorarios() {
        hiLunes = findViewById(R.id.hi_lunes);
        TimePicker hiLunesTp = new TimePicker(hiLunes, this);
        hiMartes = findViewById(R.id.hi_martes);
        TimePicker hiMartesTp = new TimePicker(hiMartes, this);
        hiMiercoles = findViewById(R.id.hi_miercoles);
        TimePicker hiMiercolesTp = new TimePicker(hiMiercoles, this);
        hiJueves = findViewById(R.id.hi_jueves);
        TimePicker hiJuevesTp = new TimePicker(hiJueves, this);
        hiViernes = findViewById(R.id.hi_viernes);
        TimePicker hiViernesTp = new TimePicker(hiViernes, this);
        hiSabado = findViewById(R.id.hi_sabado);
        TimePicker hiSabadoTp = new TimePicker(hiSabado, this);
        hiDomingo = findViewById(R.id.hi_domingo);
        TimePicker hiDomingoTp = new TimePicker(hiDomingo, this);

        hfLunes = findViewById(R.id.hf_lunes);
        TimePicker hfLunesTp = new TimePicker(hfLunes, this);
        hfMartes = findViewById(R.id.hf_martes);
        TimePicker hfMartesTp = new TimePicker(hfMartes, this);
        hfMiercoles = findViewById(R.id.hf_miercoles);
        TimePicker hfMiercolesTp = new TimePicker(hfMiercoles, this);
        hfJueves = findViewById(R.id.hf_jueves);
        TimePicker hfJuevesTp = new TimePicker(hfJueves, this);
        hfViernes = findViewById(R.id.hf_viernes);
        TimePicker hfViernesTp = new TimePicker(hfViernes, this);
        hfSabado = findViewById(R.id.hf_sabado);
        TimePicker hfSabadoTp = new TimePicker(hfSabado, this);
        hfDomingo = findViewById(R.id.hf_domingo);
        TimePicker hfDomingoTp = new TimePicker(hfDomingo, this);

        hhiLunes = findViewById(R.id.hhi_lunes);
        TimePicker hhiLunesTp = new TimePicker(hhiLunes, this);
        hhiMartes = findViewById(R.id.hhi_martes);
        TimePicker hhiMartesTp = new TimePicker(hhiMartes, this);
        hhiMiercoles = findViewById(R.id.hhi_miercoles);
        TimePicker hhiMiercolesTp = new TimePicker(hhiMiercoles, this);
        hhiJueves = findViewById(R.id.hhi_jueves);
        TimePicker hhiJuevesTp = new TimePicker(hhiJueves, this);
        hhiViernes = findViewById(R.id.hhi_viernes);
        TimePicker hhiViernesTp = new TimePicker(hhiViernes, this);
        hhiSabado = findViewById(R.id.hhi_sabado);
        TimePicker hhiSabadoTp = new TimePicker(hhiSabado, this);
        hhiDomingo = findViewById(R.id.hhi_domingo);
        TimePicker hhiDomingoTp = new TimePicker(hhiDomingo, this);

        hhfLunes = findViewById(R.id.hhf_lunes);
        TimePicker hhfLunesTp = new TimePicker(hhfLunes, this);
        hhfMartes = findViewById(R.id.hhf_martes);
        TimePicker hhfMartesTp = new TimePicker(hhfMartes, this);
        hhfMiercoles = findViewById(R.id.hhf_miercoles);
        TimePicker hhfMiercolesTp = new TimePicker(hhfMiercoles, this);
        hhfJueves = findViewById(R.id.hhf_jueves);
        TimePicker hhfJuevesTp = new TimePicker(hhfJueves, this);
        hhfViernes = findViewById(R.id.hhf_viernes);
        TimePicker hhfViernesTp = new TimePicker(hhfViernes, this);
        hhfSabado = findViewById(R.id.hhf_sabado);
        TimePicker hhfSabadoTp = new TimePicker(hhfSabado, this);
        hhfDomingo = findViewById(R.id.hhf_domingo);
        TimePicker hhfDomingoTp = new TimePicker(hhfDomingo, this);
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }


}
