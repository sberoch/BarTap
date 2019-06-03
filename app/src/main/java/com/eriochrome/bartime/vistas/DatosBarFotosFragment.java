package com.eriochrome.bartime.vistas;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.utils.FragmentChangeListener;

public class DatosBarFotosFragment extends Fragment {

    private Button continuar;
    private Button nueva;
    private RecyclerView recyclerView;
    private TextView fotosRestantes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_datos_bar_fotos, container, false);

        fotosRestantes = view.findViewById(R.id.fotosRestantes);
        recyclerView = view.findViewById(R.id.recyclerView);
        nueva = view.findViewById(R.id.nueva);
        continuar = view.findViewById(R.id.continuar);

        continuar.setOnClickListener(v -> {
            FragmentChangeListener changeListener = (FragmentChangeListener) getActivity();
            changeListener.replaceFragment(new DatosBarHorariosFragment());
        });
        return view;
    }

}
