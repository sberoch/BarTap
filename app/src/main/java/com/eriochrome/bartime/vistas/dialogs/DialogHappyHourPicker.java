package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.super_rabbit.wheel_picker.WheelPicker;

public class DialogHappyHourPicker extends DialogFragment {

    private WheelPicker desdeNP;
    private WheelPicker hastaNP;
    private String horaInicial;
    private String horaFinal;

    private TextView textView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_hh_picker, null));

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            String str = horaInicial + " - " + horaFinal;
            textView.setText(str);
            dismiss();
        });
        builder.setNegativeButton(R.string.cancelar, ((dialog, which) -> dismiss()));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        desdeNP = ((AlertDialog)getDialog()).findViewById(R.id.desde_np);
        hastaNP = ((AlertDialog)getDialog()).findViewById(R.id.hasta_np);

        horaInicial = "0";
        horaFinal = "23";

        desdeNP.setMin(0);
        desdeNP.setMax(23);
        desdeNP.setSelectorRoundedWrapPreferred(true);
        desdeNP.setSelectedTextColor(R.color.colorAccent);
        desdeNP.setOnValueChangeListener((picker, oldVal, newVal) -> horaInicial = newVal);

        hastaNP.setMin(0);
        hastaNP.setMax(23);
        hastaNP.setSelectorRoundedWrapPreferred(true);
        hastaNP.setSelectedTextColor(R.color.colorAccent);
        hastaNP.setOnValueChangeListener((picker, oldVal, newVal) -> horaFinal = newVal);
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

