package com.eriochrome.bartime.vistas.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.modelos.ItemTienda;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

/**
 * Aclaracion, se hace ese bardo raro para obtener precio y descripcion
 * ya que puede no elegirse el precio por defecto del item de la lista.
 */
public class DialogCrearItemTienda extends DialogFragment {

    public interface CrearItemListener {
        void crearItem(ItemTienda itemTienda);
    }

    CrearItemListener listener;
    private ArrayList<ItemTienda> itemsTienda;
    private ArrayAdapter<String> adapter;
    private EditText precio;
    private Spinner spinnerItems;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CrearItemListener) context;
        } catch (ClassCastException e) {
            toastShort(context, "No se implemento la interfaz");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_crear_item_tienda, null));

        builder.setTitle(getString(R.string.crear_nuevo_item));

        builder.setPositiveButton(R.string.crear, (dialogInterface, i) -> {
            listener.crearItem(getItem());
            dismiss();
        });

        builder.setNegativeButton(R.string.cancelar, (dialogInterface, i) -> dismiss());

        return builder.create();
    }

    private ItemTienda getItem() {
        int precioSeleccionado = Integer.valueOf(precio.getText().toString());
        String descripcionSeleccionada = (String) spinnerItems.getSelectedItem();
        return new ItemTienda(descripcionSeleccionada, precioSeleccionado);
    }


    @Override
    public void onStart() {
        super.onStart();
        cargarPosibilidades();

        precio = ((AlertDialog)getDialog()).findViewById(R.id.precio);

        spinnerItems = ((AlertDialog)getDialog()).findViewById(R.id.spinner_items);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getItemsParaSpinner());
        spinnerItems.setAdapter(adapter);

        spinnerItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String precioText = Integer.toString(itemsTienda.get(position).getCosto());
                precio.setText(precioText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                precio.setText("");
            }
        });
    }

    private ArrayList<String> getItemsParaSpinner() {
        ArrayList<String> devolver = new ArrayList<>();
        for (ItemTienda item : itemsTienda) {
            devolver.add(item.getDescripcion());
        }
        return devolver;
    }

    private void cargarPosibilidades() {
        //En un futuro cambiar esto
        itemsTienda = new ArrayList<>();
        itemsTienda.add(new ItemTienda("10% de descuento en la siguiente compra.", 150));
        itemsTienda.add(new ItemTienda("25% de descuento en la siguiente compra.", 250));
        itemsTienda.add(new ItemTienda("50% de descuento en la siguiente compra.", 400));
        itemsTienda.add(new ItemTienda("2x1 en la siguiente compra.", 600));
    }
}
