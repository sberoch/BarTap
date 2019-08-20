package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import android.widget.EditText;
import android.widget.RatingBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.Comentario;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DialogComentario extends DialogFragment {

    public interface ComentarioListener {
        void enviarComentario(Comentario comentario);
    }

    ComentarioListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ComentarioListener) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_comentario);
        builder.setTitle("Calificar al Bar");
        builder.setPositiveButton(R.string.listo, (dialog, which) -> {
            Comentario comentario = crearComentario();
            listener.enviarComentario(comentario);
            dismiss();
        });

        return builder.create();
    }

    private Comentario crearComentario() {
        Comentario comentario = new Comentario();

        RatingBar ratingBar = ((AlertDialog)getDialog()).findViewById(R.id.ratingBar);
        EditText comentarioText = ((AlertDialog)getDialog()).findViewById(R.id.comentario);

        comentario.setEstrellas((int) ratingBar.getRating());
        comentario.setComentarioText(comentarioText.getText().toString());

        return comentario;
    }

}
