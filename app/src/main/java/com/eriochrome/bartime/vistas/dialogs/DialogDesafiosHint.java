package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;

import com.eriochrome.bartime.R;

public class DialogDesafiosHint extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.hint_desafios_title));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_desafios_hint, null));

        builder.setPositiveButton(R.string.continuar, ((dialog, which) -> dismiss()));
        return builder.create();
    }
}
