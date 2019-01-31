package com.eriochrome.bartime.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.eriochrome.bartime.R;

public class DialogValidarGanador extends DialogFragment {

    public interface Listener {
        void declararGanador();
    }
    private DialogValidarGanador.Listener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogValidarGanador.Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogCrearCuenta");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String texto = getString(R.string.deseas_declarar_ganador);
        builder.setMessage(texto);

        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            listener.declararGanador();
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
            dismiss();
        });

        return builder.create();
    }
}
