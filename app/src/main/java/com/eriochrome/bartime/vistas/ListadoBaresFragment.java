package com.eriochrome.bartime.vistas;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.eriochrome.bartime.contracts.BaresFragmentContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.presenters.BaresFragmentPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogSeleccionFiltros;

import java.util.ArrayList;
import java.util.Objects;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadoBaresFragment extends Fragment implements BaresFragmentContract.View{

    private Button filtrar;
    private Button verEnMapa;
    private EditText buscar;

    private RecyclerView baresRecyclerView;
    private ListaBaresAdapter baresAdapter;

    private ProgressBar loading;

    BaresFragmentPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_bares, container, false);

        filtrar = view.findViewById(R.id.filtrar);
        verEnMapa = view.findViewById(R.id.ver_en_mapa);
        buscar = view.findViewById(R.id.buscar);
        loading = view.findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        baresRecyclerView = view.findViewById(R.id.recycler_view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new BaresFragmentPresenter();
        presenter.bind(this);

        baresAdapter = new ListaBaresAdapter(getActivity());
        baresRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        baresRecyclerView.setAdapter(baresAdapter);
        setupListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.mostrarPrimerOrdenBares();
    }

    @Override
    public void cargando() {
        loading.setVisibility(View.VISIBLE);
        baresAdapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Bar> listaBares) {
        if (listaBares.size() == 0) {
            toastShort(getActivity(), getString(R.string.no_hay_resultados));
        }
        loading.setVisibility(View.GONE);
        baresAdapter.setItems(listaBares);

    }

    @Override
    public boolean filtroHappyHour(AlertDialog dialog) {
        Switch happyhour = dialog.findViewById(R.id.happyhour);
        return happyhour != null && happyhour.isChecked();
    }

    @Override
    public boolean filtroAbierto(AlertDialog dialog) {
        Switch abierto = dialog.findViewById(R.id.abierto);
        return abierto != null && abierto.isChecked();
    }

    @Override
    public boolean filtroEfectivo(AlertDialog dialog) {
        CheckBox efectivo = dialog.findViewById(R.id.efectivo);
        return efectivo != null && efectivo.isChecked();
    }

    @Override
    public boolean filtroCredito(AlertDialog dialog) {
        CheckBox credito = dialog.findViewById(R.id.credito);
        return credito != null && credito.isChecked();
    }

    @Override
    public boolean filtroDebito(AlertDialog dialog) {
        CheckBox debito = dialog.findViewById(R.id.debito);
        return debito != null && debito.isChecked();
    }

    @Override
    public String getOrdenamiento(AlertDialog dialog) {
        String ordenamiento = "";
        RadioGroup ordenamientoId = dialog.findViewById(R.id.ordenar_group);
        if (ordenamientoId != null) {
            switch (ordenamientoId.getCheckedRadioButtonId()) {
                case R.id.distancia:
                    ordenamiento = "distancia";
                    break;
                case R.id.estrellas:
                    ordenamiento = "estrellas";
                    break;
                case R.id.nombre:
                    ordenamiento = "nombre";
                    break;
            }
        }
        return ordenamiento;
    }


    private void ocultarTeclado(){
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }


    private void mostrarFiltros() {
        DialogFragment filtros = new DialogSeleccionFiltros();
        filtros.show(Objects.requireNonNull(getActivity()).getFragmentManager(), "filtros");
    }


    void aplicarFiltros(AlertDialog dialog, Location ultimaUbicacion) {
        if (getOrdenamiento(dialog).equals("distancia")) {
            if (ultimaUbicacion != null) {
                presenter.setUltimaUbicacion(ultimaUbicacion);
            } else {
                toastShort(getActivity(), "No se pudo obtener su ubicacion, se usara la establecida por defecto.");
            }
        }
        presenter.mostrarConFiltros(dialog);
    }


    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int espacioVertical = 60;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);

        baresRecyclerView.setLayoutManager(layoutManager);
        baresRecyclerView.addItemDecoration(espacioVerticalDecorator);
    }


    private void setupListeners() {
        filtrar.setOnClickListener(v -> mostrarFiltros());
        verEnMapa.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), MapaDeBaresActivity.class);
            startActivity(i);
        });
        buscar.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.buscarConPalabra(buscar.getText().toString());
                ocultarTeclado();
                return true;
            }
            return false;
        });
    }


    @Override
    public void onDestroy() {
        try {
            presenter.unbind();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Crashlytics.log("Error onDestroy bares fragment");
        }
        super.onDestroy();
    }
}
