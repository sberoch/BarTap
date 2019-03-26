package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ItemTiendaBarHolder;
import com.eriochrome.bartime.adapters.ListaItemsTiendaAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.TiendaBarContract;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.eriochrome.bartime.presenters.TiendaBarPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogCrearItemTienda;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class TiendaBarActivity extends AppCompatActivity implements
        TiendaBarContract.View,
        DialogCrearItemTienda.CrearItemListener,
        ItemTiendaBarHolder.HolderCallback {

    private TiendaBarPresenter presenter;

    private RecyclerView recyclerView;
    private ListaItemsTiendaAdapter adapter;

    private ProgressBar progressBar;

    private Button nuevoItem;
    private Button verVendidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda_bar);

        presenter = new TiendaBarPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        setupRecyclerView();
        adapter = new ListaItemsTiendaAdapter(this, true);
        adapter.setListenerBar(this);
        recyclerView.setAdapter(adapter);

        nuevoItem = findViewById(R.id.nuevoItem);
        nuevoItem.setOnClickListener(v -> {
            DialogCrearItemTienda dialogCrearItemTienda = new DialogCrearItemTienda();
            dialogCrearItemTienda.show(getFragmentManager(), "crearItemTienda");
        });

        verVendidos = findViewById(R.id.items_vendidos);
        verVendidos.setOnClickListener(v -> {
            Intent i = new Intent(TiendaBarActivity.this, ComprasBarActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.mostrarItemsTienda();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clear();
    }

    @Override
    public void finCargando(ArrayList<ItemTienda> items) {
        if (items.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        adapter.setItems(items);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void crearItem(ItemTienda itemTienda) {
        presenter.crearItem(itemTienda);
    }

    @Override
    public void onRemoveItem(ItemTienda itemTienda) {
        presenter.eliminarItem(itemTienda);
    }

    @Override
    public void onEditItem(ItemTienda itemTienda) {
        onRemoveItem(itemTienda);
        DialogCrearItemTienda dialogCrearItemTienda = new DialogCrearItemTienda();
        dialogCrearItemTienda.show(getFragmentManager(), "crearItemTienda");
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        SombraEspacioVerticalDecorator sombra = new SombraEspacioVerticalDecorator(this, R.drawable.drop_shadow);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(espacioVerticalDecorator);
        recyclerView.addItemDecoration(sombra);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.dejarDeMostrarItems();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
