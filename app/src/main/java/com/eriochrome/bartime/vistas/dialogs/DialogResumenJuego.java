package com.eriochrome.bartime.vistas.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Juego;

public class DialogResumenJuego extends DialogFragment {

    private Juego juego;

    public interface Listener {
        void intentarParticiparDeJuego(Juego juego);
    }

    private DialogResumenJuego.Listener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogResumenJuego.Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogResumenJuego");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_resumen_desafio, null));

        builder.setTitle(juego.getTipoDeJuego());

        builder.setPositiveButton(R.string.participar, (dialog, which) -> {
            listener.intentarParticiparDeJuego(juego);
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
            dismiss();
        });

        return builder.create();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        TextView resumenJuego = ((AlertDialog)getDialog()).findViewById(R.id.resumen_juego);
        TextView puntosTextView = ((AlertDialog)getDialog()).findViewById(R.id.puntos_text);

        resumenJuego.setText(juego.getTextoResumen());
        puntosTextView.setText("Premio: " +
                juego.getPuntos() + " puntos");
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }
}
