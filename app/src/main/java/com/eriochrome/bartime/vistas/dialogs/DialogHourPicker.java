package com.eriochrome.bartime.vistas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.appyvet.materialrangebar.RangeBar;
import com.eriochrome.bartime.R;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DialogHourPicker extends DialogFragment {




    public interface HourPicker {
        void obtenerInicial(int horaInicial, EditText hiEditText);
        void obtenerFinal(int horaFinal, EditText hfEditText);
    }

    private HourPicker listener;
    private RangeBar rangeBar;
    private CheckBox checkBox;
    private int horaInicial;
    private int horaFinal;
    private EditText hiEditText;
    private EditText hfEditText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (HourPicker) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_hour_picker, null));

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            listener.obtenerInicial(horaInicial, hiEditText);
            listener.obtenerFinal(horaFinal, hfEditText);
            dismiss();
        });
        builder.setNegativeButton(R.string.cancelar, ((dialog, which) -> dismiss()));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        rangeBar = ((AlertDialog)getDialog()).findViewById(R.id.range_bar);
        checkBox = ((AlertDialog)getDialog()).findViewById(R.id.cerrado_checkbox);

        horaInicial = Integer.valueOf(rangeBar.getLeftPinValue());
        horaFinal = Integer.valueOf(rangeBar.getRightPinValue()) - 24;//Supone que arranca en 28

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                horaInicial = 0;
                horaFinal = 0;
            }
        });

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

    public void setContainers(EditText hiEditText, EditText hfEditText) {
        this.hiEditText = hiEditText;
        this.hfEditText = hfEditText;
    }

    public void setContainer(RelativeLayout layout) {
        //TODO: implementar
    }
}
