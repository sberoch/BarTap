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
import android.widget.EditText;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.eriochrome.bartime.adapters.ListaBaresFavoritosAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.FavoritosFragmentContract;
import com.eriochrome.bartime.modelos.entidades.Bar;

import com.eriochrome.bartime.presenters.FavoritosFragmentPresenter;
import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadoFavoritosFragment extends Fragment implements FavoritosFragmentContract.View {

    private EditText buscar;

    private RecyclerView baresRecyclerView;
    private ListaBaresFavoritosAdapter baresAdapter;

    private ProgressBar loading;

    private FavoritosFragmentPresenter presenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        presenter = new FavoritosFragmentPresenter();
        presenter.bind(this);

        buscar = view.findViewById(R.id.buscar);
        buscar.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.buscar(buscar.getText().toString());
                ocultarTeclado();
                return true;
            }
            return false;
        });

        loading = view.findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        baresAdapter = new ListaBaresFavoritosAdapter(getActivity());
        baresRecyclerView = view.findViewById(R.id.recycler_view);
        baresRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        baresRecyclerView.setAdapter(baresAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.mostrarFavoritos();
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


    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);

        baresRecyclerView.setLayoutManager(layoutManager);
        baresRecyclerView.addItemDecoration(espacioVerticalDecorator);
    }


    private void ocultarTeclado(){
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
