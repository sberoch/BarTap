package com.eriochrome.bartime.vistas;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.utils.FragmentChangeListener;
import com.eriochrome.bartime.vistas.dialogs.DialogHappyHourPicker;
import com.eriochrome.bartime.vistas.dialogs.DialogHourPicker;

import java.util.ArrayList;
import java.util.HashMap;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DatosBarHorariosFragment extends Fragment {

    private ArrayList<TextView> listaHorarios;

    public interface OnReady {
        void onReadyHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal);
        void onReadyHappyHour(HashMap<String, Integer> hhInicial, HashMap<String, Integer> hhFinal);
    }

    private OnReady callback;

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
    private boolean tieneHappyHour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_bar_horarios, container, false);
        callback = (OnReady) getActivity();
        listaHorarios = new ArrayList<>();

        setupHorarios(view);
        continuar = view.findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> {
            if (completoHorarios()) {
                callback.onReadyHorarios(getHorariosInicial(), getHorariosFinal());
                if (tieneHappyHour) callback.onReadyHappyHour(getHappyhourInicial(), getHappyhourFinal());
                FragmentChangeListener changeListener = (FragmentChangeListener) getActivity();
                changeListener.replaceFragment(new DatosBarOpcionalesFragment());
            } else {
                toastShort(getActivity(), "Debes completar los horarios antes de continuar");
            }
        });
        return view;
    }

    private void setupHorarios(View view) {
        hLunes = view.findViewById(R.id.hLunes);
        listaHorarios.add(hLunes);
        view.findViewById(R.id.horario_lunes).setOnClickListener(v -> openHourPicker(hLunes));
        hMartes = view.findViewById(R.id.hMartes);
        listaHorarios.add(hMartes);
        view.findViewById(R.id.horario_martes).setOnClickListener(v -> openHourPicker(hMartes));
        hMiercoles = view.findViewById(R.id.hMiercoles);
        listaHorarios.add(hMiercoles);
        view.findViewById(R.id.horario_miercoles).setOnClickListener(v -> openHourPicker(hMiercoles));
        hJueves = view.findViewById(R.id.hJueves);
        listaHorarios.add(hJueves);
        view.findViewById(R.id.horario_jueves).setOnClickListener(v -> openHourPicker(hJueves));
        hViernes = view.findViewById(R.id.hViernes);
        listaHorarios.add(hViernes);
        view.findViewById(R.id.horario_viernes).setOnClickListener(v -> openHourPicker(hViernes));
        hSabado = view.findViewById(R.id.hSabado);
        listaHorarios.add(hSabado);
        view.findViewById(R.id.horario_sabado).setOnClickListener(v -> openHourPicker(hSabado));
        hDomingo = view.findViewById(R.id.hDomingo);
        listaHorarios.add(hDomingo);
        view.findViewById(R.id.horario_domingo).setOnClickListener(v -> openHourPicker(hDomingo));

        hhLunes = view.findViewById(R.id.hhLunes);
        view.findViewById(R.id.hh_lunes).setOnClickListener(v -> openHHPicker(hhLunes));
        hhMartes = view.findViewById(R.id.hhMartes);
        view.findViewById(R.id.hh_martes).setOnClickListener(v -> openHHPicker(hhMartes));
        hhMiercoles = view.findViewById(R.id.hhMiercoles);
        view.findViewById(R.id.hh_miercoles).setOnClickListener(v -> openHHPicker(hhMiercoles));
        hhJueves = view.findViewById(R.id.hhJueves);
        view.findViewById(R.id.hh_jueves).setOnClickListener(v -> openHHPicker(hhJueves));
        hhViernes = view.findViewById(R.id.hhViernes);
        view.findViewById(R.id.hh_viernes).setOnClickListener(v -> openHHPicker(hhViernes));
        hhSabado = view.findViewById(R.id.hhSabado);
        view.findViewById(R.id.hh_sabado).setOnClickListener(v -> openHHPicker(hhSabado));
        hhDomingo = view.findViewById(R.id.hhDomingo);
        view.findViewById(R.id.hh_domingo).setOnClickListener(v -> openHHPicker(hhDomingo));
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

    private Integer getHorarioInicial(TextView tv) {
        try {
            String str = tv.getText().toString();
            if (str.equals("Cerrado"))
                return 0;
            return Integer.valueOf(str.split(" - ")[0]);
        } catch (RuntimeException e) {
            toastShort(getActivity(), "Ocurrio un error inesperado.");
            getActivity().finish();
            return 0;
        }
    }

    private Integer getHorarioFinal(TextView tv) {
        try {
            String str = tv.getText().toString();
            if (str.equals("Cerrado"))
                return 0;
            return Integer.valueOf(str.split(" - ")[1]);
        } catch (RuntimeException e) {
            toastShort(getActivity(), "Ocurrio un error inesperado.");
            getActivity().finish();
            return 0;
        }
    }

    private boolean completoHorarios() {
        for (TextView horario : listaHorarios) {
            if (horario.getText().equals(" - ") || horario.getText().equals("0 - 0"))
                return false;
        }
        return true;
    }

}
