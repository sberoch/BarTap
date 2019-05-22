package com.eriochrome.bartime.vistas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.eriochrome.bartime.R;

public class DialogHappyHourPicker extends DialogFragment {

    private RangeBar rangeBar;
    private int horaInicial;
    private int horaFinal;

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
        rangeBar = ((AlertDialog)getDialog()).findViewById(R.id.range_bar);

        horaInicial = Integer.valueOf(rangeBar.getLeftPinValue());
        horaFinal = Integer.valueOf(rangeBar.getRightPinValue()) - 24; //Supone que arranca en 28

        rangeBar.setOnRangeBarChangeListener(
                (rangeBar, leftPinIndex, rightPinIndex, leftPinValue, rightPinValue) -> {
                    //TODO: falta cambiar el texto de los pins si es mayor que 24.
                    int valIzq = Integer.valueOf(leftPinValue);
                    if (valIzq >= 24) {
                        valIzq = valIzq - 24;
                    }
                    horaInicial = valIzq;

                    int valDer = Integer.valueOf(rightPinValue);
                    if (valDer >= 24) {
                        valDer = valDer - 24;
                    }
                    horaFinal = valDer;
                });
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}

