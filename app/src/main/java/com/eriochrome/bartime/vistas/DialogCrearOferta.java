package com.eriochrome.bartime.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import com.eriochrome.bartime.R;

import static com.eriochrome.bartime.utils.Utils.toastShort;

//TODO: que la fecha sea con un datepicker, hacer tambien horas

public class DialogCrearOferta extends DialogFragment {

    public interface Listener {
        void crearOferta(AlertDialog dialog);
    }

    Listener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_crear_oferta, null));

        builder.setTitle(getString(R.string.crear_oferta));
        builder.setPositiveButton(getString(R.string.crear), (dialog, which) -> {
            listener.crearOferta((AlertDialog) dialog);
            dismiss();
        });
        builder.setNegativeButton(getString(R.string.cancelar), ((dialog, which) -> dismiss()));

        return builder.create();
    }
}
