package com.eriochrome.bartime.vistas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.ItemTienda;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DialogComprarItemTienda extends DialogFragment {

    public interface ComprarListener {
        void onItemComprado(ItemTienda itemTienda);
    }

    private ItemTienda itemTienda;
    private int misPuntos;
    private ComprarListener listener;
    private TextView misPuntosText;
    private TextView costoText;
    private TextView totalText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ComprarListener) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_comprar_item_tienda, null));

        builder.setPositiveButton(R.string.comprar, (dialogInterface, i) -> {
            listener.onItemComprado(itemTienda);
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialogInterface, i) -> dismiss());

        return builder.create();
    }


    public void setup(int misPuntos, ItemTienda itemTienda) {
        this.itemTienda = itemTienda;
        this.misPuntos = misPuntos;
    }

    @Override
    public void onStart() {
        super.onStart();
        misPuntosText = ((AlertDialog)getDialog()).findViewById(R.id.actual);
        costoText = ((AlertDialog)getDialog()).findViewById(R.id.costo);
        totalText = ((AlertDialog)getDialog()).findViewById(R.id.total);

        misPuntosText.setText(String.valueOf(misPuntos));
        costoText.setText(String.valueOf(-itemTienda.getCosto()));
        totalText.setText(String.valueOf(misPuntos-itemTienda.getCosto()));
    }
}
