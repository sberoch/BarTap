package com.eriochrome.bartime;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.app.DialogFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadoBaresFragment extends Fragment implements ReferenciaDatabase,  SeleccionFiltros.FiltrosListener{

    private DatabaseReference refGlobal;
    private DatabaseReference refBares;
    private boolean ordenDescendente;

    private Button filtrar;
    private EditText buscar;

    private RecyclerView baresRecyclerView;
    private ListaBaresAdapter baresAdapter;
    private ArrayList<Bar> listaBares;

    private ProgressBar loading;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_bares, container, false);

        filtrar = view.findViewById(R.id.filtrar);
        buscar = view.findViewById(R.id.buscar);

        listaBares = new ArrayList<>();
        baresRecyclerView = view.findViewById(R.id.recycler_view);

        baresAdapter = new ListaBaresAdapter(getActivity(), listaBares);
        baresRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        baresRecyclerView.setAdapter(baresAdapter);

        ordenDescendente = false;

        loading = view.findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        setupListeners();
        return view;
    }


    @Override
    public void setReferenciaADatabase(DatabaseReference ref) {
        this.refGlobal = ref;
        this.refBares = refGlobal.child("bares");
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cargando();
        refBares.orderByChild("estrellas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot barSnapshot : dataSnapshot.getChildren()) {
                    Bar bar = barSnapshot.getValue(Bar.class);
                    listaBares.add(0, bar);
                }
                finCargando(listaBares.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                toastShort(getActivity(), "Error al leer base de datos.");
            }
        });
    }


    private void buscarBarConPalabra(String s) {
        cargando();
        final String busqueda = s.toLowerCase();
        refBares.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBares.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nombreBar = ds.child("nombre").getValue(String.class).toLowerCase();
                    if (nombreBar.contains(busqueda)) {
                        listaBares.add(ds.getValue(Bar.class));
                    }
                }
                finCargando(listaBares.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                toastShort(getActivity(), "Ocurrio un error inesperado");
            }
        });
    }


    private void cargando() {
        loading.setVisibility(View.VISIBLE);
        listaBares.clear();
        baresAdapter.notifyDataSetChanged();
    }


    private void finCargando(long cantResultados) {
        if (cantResultados == 0) {
            toastShort(getActivity(), "No hay resultados");
        }
        loading.setVisibility(View.GONE);
        baresAdapter.notifyDataSetChanged();
    }


    private void ocultarTeclado(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }


    private void mostrarFiltros() {
        DialogFragment filtros = new SeleccionFiltros();
        filtros.show(getFragmentManager(), "filtros");
    }


    @Override
    public void aplicarFiltros(DialogFragment dialogFragment, AlertDialog dialog) {
        cargando();
        Query query = obtenerQuery(dialog);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot barSnap : dataSnapshot.getChildren()) {
                    listaBares.add(barSnap.getValue(Bar.class));
                }
                if(ordenDescendente) {
                    Collections.reverse(listaBares );
                }
                finCargando(listaBares.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                toastShort(getActivity(), "Ocurrio un error inesperado");
                getActivity().finish();
            }
        });
    }


    /**
     * Achica el query segun los datos marcados en el dialog.
     *
     * NOTAR: hay bares que estan en otra rama del json en firebase para poder combinar filtros.
     */
    private Query obtenerQuery(AlertDialog dialog) {
        ordenDescendente = false;
        Query query = refBares;
        Switch hayOfertas = dialog.findViewById(R.id.descuentos);
        RadioGroup ordenamientos = dialog.findViewById(R.id.ordenar_group);
        //Filtro: bares en oferta
        if(hayOfertas.isChecked()) {
            query = refGlobal.child("baresConOferta");
        }
        //Ordeno segun lo elegido
        switch (ordenamientos.getCheckedRadioButtonId()) {
            case R.id.distancia:
                //TODO: distancias (geoloc)
                query = query.orderByChild("estrellas");
                toastShort(getActivity(), "Ojota, es por estrellas, arreglar.");
                break;

            case R.id.estrellas:
                query = query.orderByChild("estrellas");
                ordenDescendente = true;
                break;

            case R.id.nombre:
                query = query.orderByChild("nombre");
                break;
        }
        return query;
    }


    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        SombraEspacioVerticalDecorator sombra = new SombraEspacioVerticalDecorator(getActivity(), R.drawable.drop_shadow);

        baresRecyclerView.setLayoutManager(layoutManager);
        baresRecyclerView.addItemDecoration(espacioVerticalDecorator);
        baresRecyclerView.addItemDecoration(sombra);
    }


    private void setupListeners() {
        filtrar.setOnClickListener(v -> mostrarFiltros());
        buscar.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                buscarBarConPalabra(buscar.getText().toString());
                ocultarTeclado();
                return true;
            }
            return false;
        });
    }
}
