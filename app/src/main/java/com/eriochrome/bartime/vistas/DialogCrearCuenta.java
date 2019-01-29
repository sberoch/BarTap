package com.eriochrome.bartime.vistas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;

import com.eriochrome.bartime.R;

public class DialogCrearCuenta extends DialogFragment {

    public interface Listener {
        void login();
    }

    private String texto;
    private Listener listener;

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogCrearCuenta");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(texto)
               .setTitle(getString(R.string.se_requiere_cuenta));

        builder.setPositiveButton(R.string.crear_cuenta, (dialog, which) -> {
            listener.login();
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
            dismiss();
        });

        return builder.create();
    }
}
