package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Bar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogReclamarBar extends DialogFragment {

    private Bar bar;

    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public interface Listener {
        void reclamarBar(Bar bar);
    }

    private Listener listener;

    @Override
    public void onAttach(@NonNull Context context) {
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
        builder.setMessage(getString(R.string.deseas_reclamar_este_bar))
               .setTitle(getString(R.string.reclamar_bar));

        builder.setPositiveButton(R.string.reclamar, (dialog, which) -> {
            listener.reclamarBar(bar);
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
            dismiss();
        });

        return builder.create();
    }
}
