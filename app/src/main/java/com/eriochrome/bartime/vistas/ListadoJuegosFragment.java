package com.eriochrome.bartime.vistas;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
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

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaJuegosAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.JuegosFragmentContract;
import com.eriochrome.bartime.modelos.Juego;
import com.eriochrome.bartime.presenters.JuegosFragmentPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;


public class ListadoJuegosFragment extends Fragment implements JuegosFragmentContract.View {

    private JuegosFragmentPresenter presenter;

    private Button filtrar;
    private EditText buscar;

    private RecyclerView juegosRecyclerView;
    private ListaJuegosAdapter juegosAdapter;

    private ProgressBar loading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listado_juegos, container, false);

        presenter = new JuegosFragmentPresenter();
        presenter.bind(this);

        juegosRecyclerView = view.findViewById(R.id.recycler_view);
        juegosRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        juegosAdapter = new ListaJuegosAdapter(getActivity(), false);
        juegosRecyclerView.setAdapter(juegosAdapter);

        filtrar = view.findViewById(R.id.filtrar);
        buscar = view.findViewById(R.id.buscar);

        loading = view.findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        buscar.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.buscar(buscar.getText().toString());
                ocultarTeclado();
                return true;
            }
            return false;
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.mostrarJuegos();
    }

    public void onClickJuego(Juego juego) {
        DialogResumenJuego dialog = new DialogResumenJuego();
        dialog.setJuego(juego);
        dialog.show(getFragmentManager(), "juego");
    }


    public void participarDeJuego(Juego juego) {
        if (presenter.estaConectado()) {
            presenter.participarDeJuego(juego);
        } else {
            DialogCrearCuenta crearCuentaDialog = new DialogCrearCuenta();
            crearCuentaDialog.setTexto(getString(R.string.necesitas_cuenta_participar));
            crearCuentaDialog.show(getFragmentManager(), "crearCuentaDialog");
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        SombraEspacioVerticalDecorator sombra = new SombraEspacioVerticalDecorator(getActivity(), R.drawable.drop_shadow);

        juegosRecyclerView.setLayoutManager(layoutManager);
        juegosRecyclerView.addItemDecoration(espacioVerticalDecorator);
        juegosRecyclerView.addItemDecoration(sombra);
    }


    @Override
    public void cargando() {
        loading.setVisibility(View.VISIBLE);
        juegosAdapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Juego> juegos) {
        if (juegos.size() == 0) {
            toastShort(getActivity(), getString(R.string.no_hay_resultados));
        }
        juegosAdapter.setItems(juegos);
        loading.setVisibility(View.GONE);
    }

    @Override
    public void successParticipando() {
        toastShort(getActivity(), getString(R.string.exito_participar_juego));
    }

    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    @Override
    public void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

}
