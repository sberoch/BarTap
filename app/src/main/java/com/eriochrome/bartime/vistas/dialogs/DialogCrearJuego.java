package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.eriochrome.bartime.R;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DialogCrearJuego extends DialogFragment {

    public interface OnButtonClick {
        void crearJuegoConTipo(String tipoDeJuego);
    }

    private OnButtonClick listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnButtonClick) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.crear_juego));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_nuevo_juego, null));

        builder.setNegativeButton(R.string.cancelar, ((dialog, which) -> dismiss()));
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        Button crearDesafio = ((AlertDialog)getDialog()).findViewById(R.id.crear_desafio);
        crearDesafio.setOnClickListener(v -> {
            listener.crearJuegoConTipo("Desafio");
        });

        Button crearTrivia = ((AlertDialog)getDialog()).findViewById(R.id.crear_trivia);
        crearTrivia.setOnClickListener(v -> {
            listener.crearJuegoConTipo("Trivia");
        });

        Button crearSorteo = ((AlertDialog)getDialog()).findViewById(R.id.crear_sorteo);
        crearSorteo.setOnClickListener(v -> {
            listener.crearJuegoConTipo("Sorteo");
        });
    }
}
