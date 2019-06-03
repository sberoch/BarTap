package com.eriochrome.bartime.vistas;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.eriochrome.bartime.R;

import java.util.ArrayList;

public class DatosBarOpcionalesFragment extends Fragment {

    public interface OnReady {
        void onReadyMetodosPago(ArrayList<String> metodosDePago);
    }

    private OnReady callback;
    private CheckBox efectivo;
    private CheckBox tCredito;
    private CheckBox tDebito;
    private Button listo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_bar_opcionales, container, false);
        callback = (OnReady) getActivity();

        efectivo = view.findViewById(R.id.efectivo);
        tCredito = view.findViewById(R.id.tarjeta_credito);
        tDebito = view.findViewById(R.id.tarjeta_debito);

        listo = view.findViewById(R.id.listo);
        listo.setOnClickListener(v -> {
            callback.onReadyMetodosPago(getMetodosDePago());
            getActivity().finish();
        });
        return view;
    }

    private ArrayList<String> getMetodosDePago() {
        ArrayList<String> metodosDePago = new ArrayList<>();
        if (efectivo.isChecked()) {
            metodosDePago.add("efectivo");
        }
        if (tCredito.isChecked()) {
            metodosDePago.add("tarjeta de credito");
        }
        if (tDebito.isChecked()) {
            metodosDePago.add("tarjeta de debito");
        }
        return metodosDePago;
    }

}
