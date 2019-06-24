package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarHorariosContract;
import com.eriochrome.bartime.presenters.DatosBarHorariosPresenter;
import com.eriochrome.bartime.utils.FragmentChangeListener;
import com.eriochrome.bartime.vistas.dialogs.DialogHappyHourPicker;
import com.eriochrome.bartime.vistas.dialogs.DialogHourPicker;

import java.util.ArrayList;
import java.util.HashMap;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarHorariosActivity extends AppCompatActivity implements DatosBarHorariosContract.View {

    private DatosBarHorariosPresenter presenter;

    private Button continuar;
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
    private ArrayList<TextView> listaHorarios;
    private boolean tieneHappyHour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_horarios);
        listaHorarios = new ArrayList<>();

        presenter = new DatosBarHorariosPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        setupHorarios();
        continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> {
            if (completoHorarios()) {
                Intent i = new Intent(DatosBarHorariosActivity.this, DatosBarOpcionalesActivity.class);
                presenter.setHorarios(getHorariosInicial(), getHorariosFinal());
                if (tieneHappyHour)
                    presenter.setHappyHour(getHappyhourInicial(), getHappyhourFinal());
                i = presenter.enviarBar(i);
                startActivity(i);
            } else {
                toastShort(this, getString(R.string.debes_completar_horarios_antes_de_continuar));
            }
        });
    }

    private boolean completoHorarios() {
        for (TextView horario : listaHorarios) {
            if (horario.getText().equals(" - ") ||
                horario.getText().equals("0 - 0"))
                return false;
        }
        return true;
    }

    private void setupHorarios() {
        hLunes = findViewById(R.id.hLunes);
        listaHorarios.add(hLunes);
        findViewById(R.id.horario_lunes).setOnClickListener(v -> openHourPicker(hLunes));
        hMartes = findViewById(R.id.hMartes);
        listaHorarios.add(hMartes);
        findViewById(R.id.horario_martes).setOnClickListener(v -> openHourPicker(hMartes));
        hMiercoles = findViewById(R.id.hMiercoles);
        listaHorarios.add(hMiercoles);
        findViewById(R.id.horario_miercoles).setOnClickListener(v -> openHourPicker(hMiercoles));
        hJueves = findViewById(R.id.hJueves);
        listaHorarios.add(hJueves);
        findViewById(R.id.horario_jueves).setOnClickListener(v -> openHourPicker(hJueves));
        hViernes = findViewById(R.id.hViernes);
        listaHorarios.add(hViernes);
        findViewById(R.id.horario_viernes).setOnClickListener(v -> openHourPicker(hViernes));
        hSabado = findViewById(R.id.hSabado);
        listaHorarios.add(hSabado);
        findViewById(R.id.horario_sabado).setOnClickListener(v -> openHourPicker(hSabado));
        hDomingo = findViewById(R.id.hDomingo);
        listaHorarios.add(hDomingo);
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

    private HashMap<String, Integer> getHorariosInicial() {
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

    private HashMap<String, Integer> getHorariosFinal() {
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

    private HashMap<String, Integer> getHappyhourInicial() {
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

    private HashMap<String, Integer> getHappyhourFinal() {
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

    private Integer getHorarioInicial(TextView tv) {
        try {
            String str = tv.getText().toString();
            if (str.equals("Cerrado")) return 0;
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
            if (str.equals("Cerrado")) return 0;
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
