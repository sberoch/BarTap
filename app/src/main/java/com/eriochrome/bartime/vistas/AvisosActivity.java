package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.AvisosAdapter;
import com.eriochrome.bartime.contracts.AvisosContract;
import com.eriochrome.bartime.modelos.entidades.Aviso;
import com.eriochrome.bartime.presenters.AvisosPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class AvisosActivity extends AppCompatActivity implements AvisosContract.View, AvisosAdapter.ClickListener {

    private AvisosPresenter presenter;

    private ProgressBar progressBar;
    private ListView listView;
    private AvisosAdapter adapter;
    private ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        presenter = new AvisosPresenter();
        presenter.bind(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        volver = findViewById(R.id.volver_avisos);
        volver.setOnClickListener(v -> finish());

        listView = findViewById(R.id.listview);
        adapter = new AvisosAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarAvisos(false);
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Aviso> avisos) {
        if (avisos.size() == 0) {
            toastShort(this, "No hay nuevos avisos.");
        }
        adapter.setItems(avisos);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRemoveItem(String idItem) {
        presenter.quitarItem(idItem, false);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

}
