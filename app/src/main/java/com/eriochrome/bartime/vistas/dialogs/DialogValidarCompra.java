package com.eriochrome.bartime.vistas.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;

public class DialogValidarCompra extends DialogFragment {

    private ComprobanteDeCompra comprobanteDeCompra;

    public void setComprobante(ComprobanteDeCompra comprobanteDeCompra) {
        this.comprobanteDeCompra = comprobanteDeCompra;
    }

    public interface OnValidarListener {
        void onValidar(ComprobanteDeCompra comprobanteDeCompra);
    }

    private OnValidarListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnValidarListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement interface");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String texto = getString(R.string.canjear_compra_desea_continuar);
        builder.setMessage(texto);

        builder.setPositiveButton(R.string.validar, (dialog, which) -> {
            listener.onValidar(comprobanteDeCompra);
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialog, which) -> {
            dismiss();
        });

        return builder.create();
    }
}
