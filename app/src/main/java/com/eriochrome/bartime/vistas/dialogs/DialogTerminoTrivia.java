package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.vistas.ListadosActivity;

public class DialogTerminoTrivia extends DialogFragment {

    private String texto;

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(texto);
        builder.setPositiveButton(R.string.continuar, ((dialog, which) -> {
            startActivity(new Intent(getActivity(), ListadosActivity.class));
            getActivity().finish();
        }));
        return builder.create();
    }

}
