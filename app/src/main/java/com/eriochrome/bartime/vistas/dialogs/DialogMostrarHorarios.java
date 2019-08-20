package com.eriochrome.bartime.vistas.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogMostrarHorarios extends DialogFragment {

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

    private RelativeLayout happyHourRL;
    private Bar bar;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_mostrar_horarios, null));

        builder.setPositiveButton(R.string.volver, ((dialog, which) -> dismiss()));

        return builder.create();
    }

    public void setHorarios(Bar bar) {
        this.bar = bar;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = (AlertDialog) getDialog();
        hLunes = dialog.findViewById(R.id.hLunes);
        hMartes = dialog.findViewById(R.id.hMartes);
        hMiercoles = dialog.findViewById(R.id.hMiercoles);
        hJueves = dialog.findViewById(R.id.hJueves);
        hViernes = dialog.findViewById(R.id.hViernes);
        hSabado = dialog.findViewById(R.id.hSabado);
        hDomingo = dialog.findViewById(R.id.hDomingo);

        setupHorarios(bar.getHorariosInicial(), bar.getHorariosFinal());

        if (tieneHappyHour(bar)) {
            hhLunes = dialog.findViewById(R.id.hhLunes);
            hhMartes = dialog.findViewById(R.id.hhMartes);
            hhMiercoles = dialog.findViewById(R.id.hhMiercoles);
            hhJueves = dialog.findViewById(R.id.hhJueves);
            hhViernes = dialog.findViewById(R.id.hhViernes);
            hhSabado = dialog.findViewById(R.id.hhSabado);
            hhDomingo = dialog.findViewById(R.id.hhDomingo);
            setupHappyHour(bar.getHorariosInicial(), bar.getHorariosFinal());
        } else {
            happyHourRL = dialog.findViewById(R.id.hh_rl);
            happyHourRL.setVisibility(View.GONE);
        }
    }

    public void setupHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        hLunes.setText(formatHorario(horariosInicial.get("Lunes"), horariosFinal.get("Lunes")));
        hMartes.setText(formatHorario(horariosInicial.get("Martes"), horariosFinal.get("Martes")));
        hMiercoles.setText(formatHorario(horariosInicial.get("Miercoles"), horariosFinal.get("Miercoles")));
        hJueves.setText(formatHorario(horariosInicial.get("Jueves"), horariosFinal.get("Jueves")));
        hViernes.setText(formatHorario(horariosInicial.get("Viernes"), horariosFinal.get("Viernes")));
        hSabado.setText(formatHorario(horariosInicial.get("Sabado"), horariosFinal.get("Sabado")));
        hDomingo.setText(formatHorario(horariosInicial.get("Domingo"), horariosFinal.get("Domingo")));
    }

    public void setupHappyHour(HashMap<String, Integer> happyhourInicial, HashMap<String, Integer> happyhourFinal) {
        hhLunes.setText(formatHorario(happyhourInicial.get("Lunes"), happyhourFinal.get("Lunes")));
        hhMartes.setText(formatHorario(happyhourInicial.get("Martes"), happyhourFinal.get("Martes")));
        hhMiercoles.setText(formatHorario(happyhourInicial.get("Miercoles"), happyhourFinal.get("Miercoles")));
        hhJueves.setText(formatHorario(happyhourInicial.get("Jueves"), happyhourFinal.get("Jueves")));
        hhViernes.setText(formatHorario(happyhourInicial.get("Viernes"), happyhourFinal.get("Viernes")));
        hhSabado.setText(formatHorario(happyhourInicial.get("Sabado"), happyhourFinal.get("Sabado")));
        hhDomingo.setText(formatHorario(happyhourInicial.get("Domingo"), happyhourFinal.get("Domingo")));
    }

    @SuppressLint("DefaultLocale")
    private String formatHorario(Integer ini, Integer fin) {
        if (ini == 0 && fin == 0) return getString(R.string.cerrado);
        return String.format("%d - %d", ini, fin);
    }

    private boolean tieneHappyHour(Bar bar) {
        boolean hayDiaConHHInicial = false;
        boolean hayDiaConHHFinal = false;
        for (Integer hhInicial : bar.getHappyhourInicial().values()) {
            if (hhInicial != 0) hayDiaConHHInicial = true;
        }
        for (Integer hhFinal : bar.getHappyhourFinal().values()) {
            if (hhFinal != 0) hayDiaConHHFinal = true;
        }
        return hayDiaConHHInicial && hayDiaConHHFinal;
    }
}
