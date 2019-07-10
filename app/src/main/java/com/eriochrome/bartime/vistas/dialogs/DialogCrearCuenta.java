package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
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
