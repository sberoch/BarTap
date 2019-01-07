package com.eriochrome.bartime.vistas;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.google.firebase.database.DatabaseReference;


public class ListadoJuegosFragment extends Fragment {

    private Button filtrar;
    private EditText buscar;

    private RecyclerView juegosRecyclerView;
    private ListaBaresAdapter desfiosAdapter;

    private ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_juegos, container, false);

        filtrar = view.findViewById(R.id.filtrar);
        buscar = view.findViewById(R.id.buscar);

        return view;
    }

}
