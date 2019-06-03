package com.eriochrome.bartime.vistas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarOwnerContract;
import com.eriochrome.bartime.presenters.DatosBarOwnerPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogHappyHourPicker;
import com.eriochrome.bartime.vistas.dialogs.DialogHourPicker;

import java.util.ArrayList;
import java.util.HashMap;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarOwnerActivity extends AppCompatActivity implements
        DatosBarOwnerContract.View {

    private static final int NUMERO_SOLICITUD_GALERIA = 1;
    private static final int NUMERO_SOLICITUD_UBICACION = 2;

    private DatosBarOwnerPresenter presenter;

    private ProgressBar progressBar;
    private RelativeLayout agregarBarRl;
    private TextView titulo;

    //private EditText nombreBar;
    //private Button seleccionarFoto;
    private Button seleccionarUbicacion;
    private boolean hayImagen;

    /**
     * h: horario
     * hh: happy hour
     */

    private TextView hLunes, hhLunes;
    private TextView hMartes, hhMartes;
    private TextView hMiercoles, hhMiercoles;
    private TextView hJueves, hhJueves;
    private TextView hViernes, hhViernes;
    private TextView hSabado, hhSabado;
    private TextView hDomingo, hhDomingo;

    private RadioButton efectivo;
    private RadioButton tCredito;
    private RadioButton tDebito;
    private Button listo;

    private boolean tieneHappyHour;

    View.OnFocusChangeListener changeListener = (v, hasFocus) -> {
        if (!hasFocus) ocultarTeclado();
    };
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_owner);

        presenter = new DatosBarOwnerPresenter();
        presenter.bind(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        agregarBarRl = findViewById(R.id.agregar_bar_rl);
        titulo = findViewById(R.id.titulo);

        //nombreBar = findViewById(R.id.nombre_bar_edit_text);
        //seleccionarFoto = findViewById(R.id.seleccionar_imagen);
        seleccionarUbicacion = findViewById(R.id.seleccionar_ubicacion);
        setupHorarios();
        efectivo = findViewById(R.id.efectivo);
        tCredito = findViewById(R.id.tarjeta_credito);
        tDebito = findViewById(R.id.tarjeta_debito);
        listo = findViewById(R.id.listo);

        setupListeners();
        presenter.checkUsarDatosBar(getIntent());

        //TODO: hacer preview de foto
        //TODO: preguntar si quiere reclamar un bar sin dueño
    }


    private void setupListeners() {
        listo.setOnClickListener(v -> {
            presenter.listo();
            finish();
        });
        //seleccionarFoto.setOnClickListener(v -> seleccionarImagenDeGaleria());
        seleccionarUbicacion.setOnClickListener(v -> {
            Intent i = new Intent(DatosBarOwnerActivity.this, SeleccionarUbicacionActivity.class);
            startActivityForResult(i, NUMERO_SOLICITUD_UBICACION);
        });
        //nombreBar.setOnFocusChangeListener(changeListener);
    }


    @Override
    public String getTextNombreBar() {
        //return nombreBar.getText().toString();
        return null;
    }

    @Override
    public HashMap<String, Integer> getHorariosInicial() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo", getHorarioInicial(hDomingo));
        devolver.put("Lunes", getHorarioInicial(hLunes));
        devolver.put("Martes", getHorarioInicial(hMartes));
        devolver.put("Miercoles", getHorarioInicial(hMiercoles));
        devolver.put("Jueves", getHorarioInicial(hJueves));
        devolver.put("Viernes", getHorarioInicial(hViernes));
        devolver.put("Sabado", getHorarioInicial(hSabado));
        return devolver;
    }

    @Override
    public HashMap<String, Integer> getHorariosFinal() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo", getHorarioFinal(hDomingo));
        devolver.put("Lunes", getHorarioFinal(hLunes));
        devolver.put("Martes", getHorarioFinal(hMartes));
        devolver.put("Miercoles", getHorarioFinal(hMiercoles));
        devolver.put("Jueves", getHorarioFinal(hJueves));
        devolver.put("Viernes", getHorarioFinal(hViernes));
        devolver.put("Sabado", getHorarioFinal(hSabado));
        return devolver;
    }

    @Override
    public boolean tieneHappyHour() {
        return tieneHappyHour;
    }

    @Override
    public HashMap<String, Integer> getHappyhourInicial() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo", getHorarioInicial(hhDomingo));
        devolver.put("Lunes", getHorarioInicial(hhLunes));
        devolver.put("Martes", getHorarioInicial(hhMartes));
        devolver.put("Miercoles", getHorarioInicial(hhMiercoles));
        devolver.put("Jueves", getHorarioInicial(hhJueves));
        devolver.put("Viernes", getHorarioInicial(hhViernes));
        devolver.put("Sabado", getHorarioInicial(hhSabado));
        return devolver;
    }

    @Override
    public HashMap<String, Integer> getHappyhourFinal() {
        HashMap<String, Integer> devolver = new HashMap<>();
        devolver.put("Domingo", getHorarioFinal(hhDomingo));
        devolver.put("Lunes", getHorarioFinal(hhLunes));
        devolver.put("Martes", getHorarioFinal(hhMartes));
        devolver.put("Miercoles", getHorarioFinal(hhMiercoles));
        devolver.put("Jueves", getHorarioFinal(hhJueves));
        devolver.put("Viernes", getHorarioFinal(hhViernes));
        devolver.put("Sabado", getHorarioFinal(hhSabado));
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
        if (requestCode == NUMERO_SOLICITUD_GALERIA) {
            if (resultCode == RESULT_OK) {
                Uri path = data.getData();
                presenter.agregarFoto(path);
                hayImagen = true;
            }
            else {
                toastShort(DatosBarOwnerActivity.this, "No elegiste ninguna imagen.");
            }
        }
        else if (requestCode == NUMERO_SOLICITUD_UBICACION) {
            if (resultCode == RESULT_OK) {
                lat = data.getDoubleExtra("latitud", 0);
                lng = data.getDoubleExtra("longitud", 0);
                String direccion = data.getStringExtra("direccion");
                seleccionarUbicacion.setText(direccion);
            }
        }
    }

    @Override
    public boolean hayImagen() {
        return hayImagen;
    }

    @Override
    public void noHayImagenError() {
        toastShort(DatosBarOwnerActivity.this, "Debes elegir una imagen para continuar.");
    }

    @Override
    public void mostrarError() {
        toastShort(DatosBarOwnerActivity.this, "Ocurrio un error inesperado. Intente nuevamente");
    }


    @Override
    public void subiendo() {
        progressBar.setVisibility(View.VISIBLE);
        agregarBarRl.setVisibility(View.GONE);
    }

    @Override
    public void finSubiendo() {
        progressBar.setVisibility(View.GONE);
        startActivity(new Intent(DatosBarOwnerActivity.this, BarControlActivity.class));
        finish();
    }


    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }


    private void setupHorarios() {
        hLunes = findViewById(R.id.hLunes);
        findViewById(R.id.horario_lunes).setOnClickListener(v -> openHourPicker(hLunes));
        hMartes = findViewById(R.id.hMartes);
        findViewById(R.id.horario_martes).setOnClickListener(v -> openHourPicker(hMartes));
        hMiercoles = findViewById(R.id.hMiercoles);
        findViewById(R.id.horario_miercoles).setOnClickListener(v -> openHourPicker(hMiercoles));
        hJueves = findViewById(R.id.hJueves);
        findViewById(R.id.horario_jueves).setOnClickListener(v -> openHourPicker(hJueves));
        hViernes = findViewById(R.id.hViernes);
        findViewById(R.id.horario_viernes).setOnClickListener(v -> openHourPicker(hViernes));
        hSabado = findViewById(R.id.hSabado);
        findViewById(R.id.horario_sabado).setOnClickListener(v -> openHourPicker(hSabado));
        hDomingo = findViewById(R.id.hDomingo);
        findViewById(R.id.horario_domingo).setOnClickListener(v -> openHourPicker(hDomingo));

        hhLunes = findViewById(R.id.hhLunes);
        findViewById(R.id.hh_lunes).setOnClickListener(v -> openHHPicker(hhLunes));
        hhMartes = findViewById(R.id.hhMartes);
        findViewById(R.id.hh_martes).setOnClickListener(v -> openHHPicker(hhMartes));
        hhMiercoles = findViewById(R.id.hhMiercoles);
        findViewById(R.id.hh_miercoles).setOnClickListener(v -> openHHPicker(hhMiercoles));
        hhJueves = findViewById(R.id.hhJueves);
        findViewById(R.id.hh_jueves).setOnClickListener(v -> openHHPicker(hhJueves));
        hhViernes = findViewById(R.id.hhViernes);
        findViewById(R.id.hh_viernes).setOnClickListener(v -> openHHPicker(hhViernes));
        hhSabado = findViewById(R.id.hhSabado);
        findViewById(R.id.hh_sabado).setOnClickListener(v -> openHHPicker(hhSabado));
        hhDomingo = findViewById(R.id.hhDomingo);
        findViewById(R.id.hh_domingo).setOnClickListener(v -> openHHPicker(hhDomingo));
    }

    private void openHourPicker(TextView tv) {
        DialogHourPicker hourPicker = new DialogHourPicker();
        hourPicker.setTextView(tv);
        hourPicker.show(getFragmentManager(), "hourPicker");
    }

    private void openHHPicker(TextView tv) {
        DialogHappyHourPicker hhPicker = new DialogHappyHourPicker();
        hhPicker.setTextView(tv);
        hhPicker.show(getFragmentManager(), "hhPicker");
        tieneHappyHour = true;
    }

    @Override
    public String getDireccion() {
        return seleccionarUbicacion.getText().toString();
    }

    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLng() {
        return lng;
    }


    /**
     * ------------------------Seccion editar----------------------------
     */

    @Override
    public void setNombreBar(String nombre) {
        //nombreBar.setText(nombre);
    }

    @Override
    public void setHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        hLunes.setText(formatHorario(horariosInicial.get("Lunes"), horariosFinal.get("Lunes")));
        hMartes.setText(formatHorario(horariosInicial.get("Martes"), horariosFinal.get("Martes")));
        hMiercoles.setText(formatHorario(horariosInicial.get("Miercoles"), horariosFinal.get("Miercoles")));
        hJueves.setText(formatHorario(horariosInicial.get("Jueves"), horariosFinal.get("Jueves")));
        hViernes.setText(formatHorario(horariosInicial.get("Viernes"), horariosFinal.get("Viernes")));
        hSabado.setText(formatHorario(horariosInicial.get("Sabado"), horariosFinal.get("Sabado")));
        hDomingo.setText(formatHorario(horariosInicial.get("Domingo"), horariosFinal.get("Domingo")));
    }

    @Override
    public void setHappyHours(HashMap<String, Integer> happyHourInicial, HashMap<String, Integer> happyHourFinal) {
    	hhLunes.setText(formatHorario(happyHourInicial.get("Lunes"), happyHourFinal.get("Lunes")));
        hhMartes.setText(formatHorario(happyHourInicial.get("Martes"), happyHourFinal.get("Martes")));
        hhMiercoles.setText(formatHorario(happyHourInicial.get("Miercoles"), happyHourFinal.get("Miercoles")));
        hhJueves.setText(formatHorario(happyHourInicial.get("Jueves"), happyHourFinal.get("Jueves")));
        hhViernes.setText(formatHorario(happyHourInicial.get("Viernes"), happyHourFinal.get("Viernes")));
        hhSabado.setText(formatHorario(happyHourInicial.get("Sabado"), happyHourFinal.get("Sabado")));
        hhDomingo.setText(formatHorario(happyHourInicial.get("Domingo"), happyHourFinal.get("Domingo")));
        if (!hhSabado.getText().equals("0 - 0")) tieneHappyHour = true;
    }

    @SuppressLint("DefaultLocale")
    private String formatHorario(Integer ini, Integer fin) {
        return String.format("%d - %d", ini, fin);
    }

    @Override
    public void setUbicacion(String ubicacion) {
        seleccionarUbicacion.setText(ubicacion);
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

    /**
     * Al editar se supone que ya hay una foto subida. No va a pedir otra
     */
    @Override
    public void yaTieneImagen() {
        hayImagen = true;
    }

    @Override
    public void setTitleEditar() {
        titulo.setText(getString(R.string.editar_bar));
    }

    /**
     * ---------------------------Fin seccion editar----------------------------------
     */


    private Integer getHorarioInicial(TextView tv) {
        try {
            String str = tv.getText().toString();
            return Integer.valueOf(str.split(" - ")[0]);
        } catch (RuntimeException e) {
            toastShort(this, "Ocurrio un error inesperado.");
            finish();
            return 0;
        }
    }

    private Integer getHorarioFinal(TextView tv) {
        try {
            String str = tv.getText().toString();
            return Integer.valueOf(str.split(" - ")[1]);
        } catch (RuntimeException e) {
            toastShort(this, "Ocurrio un error inesperado.");
            finish();
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
