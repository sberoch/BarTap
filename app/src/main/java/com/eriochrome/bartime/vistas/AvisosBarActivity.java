package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.AvisosAdapter;
import com.eriochrome.bartime.contracts.AvisosContract;
import com.eriochrome.bartime.modelos.Aviso;
import com.eriochrome.bartime.presenters.AvisosPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class AvisosBarActivity extends AppCompatActivity implements AvisosContract.View, AvisosAdapter.ClickListener {

    private AvisosPresenter presenter;

    private ProgressBar progressBar;
    private ListView listView;
    private AvisosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisos);

        presenter = new AvisosPresenter();
        presenter.bind(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        listView = findViewById(R.id.listview);
        adapter = new AvisosAdapter(this);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarAvisos(true);
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
        presenter.quitarItem(idItem, true);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
