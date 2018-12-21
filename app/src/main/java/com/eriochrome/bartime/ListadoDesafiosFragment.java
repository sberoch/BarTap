package com.eriochrome.bartime;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.eriochrome.bartime.modelos.Desafio;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class ListadoDesafiosFragment extends Fragment implements ReferenciaDatabase {

    private DatabaseReference refGlobal;
    private DatabaseReference refDesafios;

    private Button filtrar;
    private EditText buscar;

    private RecyclerView desafiosRecyclerView;
    private ListaBaresAdapter desfiosAdapter;
    private ArrayList<Desafio> listaDesafios;

    private ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_desafios, container, false);

        filtrar = view.findViewById(R.id.filtrar);
        buscar = view.findViewById(R.id.buscar);

        listaDesafios = new ArrayList<>();

        //TODO: terminar

        return view;
    }

    @Override
    public void setReferenciaADatabase(DatabaseReference ref) {
        this.refDesafios = ref.child("desafios");
    }
}
