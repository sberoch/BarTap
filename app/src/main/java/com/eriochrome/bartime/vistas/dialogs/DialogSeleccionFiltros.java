package com.eriochrome.bartime.vistas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;


import com.eriochrome.bartime.R;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DialogSeleccionFiltros extends DialogFragment {


    public interface FiltrosListener {
        void aplicarFiltros(AlertDialog dialog);
    }

    FiltrosListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FiltrosListener) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_filtros, null));

        builder.setPositiveButton(R.string.aplicar_filtros, (dialogInterface, i) -> {
                    AlertDialog dialog = (AlertDialog) getDialog();
                    listener.aplicarFiltros(dialog);
                    dismiss();
                })
                .setNegativeButton(R.string.cancelar, (dialogInterface, i) -> dismiss());

        return builder.create();
    }


    @Override
    public void onStart() {
        super.onStart();
        Resources res = getResources();
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Lato-Bold.ttf");

        Switch estaAbierto = ((AlertDialog)getDialog()).findViewById(R.id.abierto);
        Switch happyHour = ((AlertDialog)getDialog()).findViewById(R.id.happyhour);
        happyHour.setVisibility(View.GONE);

        estaAbierto.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) happyHour.setVisibility(View.VISIBLE);
            else happyHour.setVisibility(View.GONE);
        });

        Button aplicarFiltros = ((AlertDialog)getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        aplicarFiltros.setTextColor(res.getColor(R.color.colorPrimary));
        aplicarFiltros.setTypeface(tf);

        Button cancelar = ((AlertDialog)getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
        cancelar.setTextColor(Color.RED);
        cancelar.setTypeface(tf);
    }
}
