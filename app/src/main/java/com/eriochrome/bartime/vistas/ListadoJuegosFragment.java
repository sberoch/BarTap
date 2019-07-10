package com.eriochrome.bartime.vistas;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.eriochrome.bartime.contracts.JuegosFragmentContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.modelos.entidades.Trivia;
import com.eriochrome.bartime.presenters.JuegosFragmentPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogCrearCuenta;
import com.eriochrome.bartime.vistas.dialogs.DialogResumenJuego;

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
        dialog.show(getActivity().getFragmentManager(), "juego");
    }


    public void intentarParticiparDeJuego(Juego juego) {
        if (presenter.estaConectado()) {
            presenter.intentarParticiparDeJuego(juego);
        } else {
            DialogCrearCuenta crearCuentaDialog = new DialogCrearCuenta();
            crearCuentaDialog.setTexto(getString(R.string.necesitas_cuenta_participar));
            crearCuentaDialog.show(getActivity().getFragmentManager(), "crearCuentaDialog");
        }
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);

        juegosRecyclerView.setLayoutManager(layoutManager);
        juegosRecyclerView.addItemDecoration(espacioVerticalDecorator);
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

    @Override
    public void yaSeParticipo() {
        toastShort(getActivity(), getString(R.string.ya_participaste_juego));
    }

    @Override
    public void ingresarATrivia(Trivia trivia) {
        Intent i = new Intent(getActivity(), TriviaActivity.class);
        i.putExtra("trivia", trivia);
        startActivity(i);
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
