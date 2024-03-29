package com.eriochrome.bartime.vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ItemTiendaHolder;
import com.eriochrome.bartime.adapters.ListaItemsTiendaAdapter;
import com.eriochrome.bartime.contracts.TiendaContract;
import com.eriochrome.bartime.modelos.entidades.ItemTienda;
import com.eriochrome.bartime.presenters.TiendaPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogComprarItemTienda;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class TiendaActivity extends AppCompatActivity implements
        TiendaContract.View,
        ItemTiendaHolder.ItemTiendaClickListener,
        DialogComprarItemTienda.ComprarListener{

    private TiendaPresenter presenter;

    private RecyclerView recyclerView;
    private ListaItemsTiendaAdapter adapter;
    private TextView puntosText;

    private ProgressBar progressBar;
    private ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        presenter = new TiendaPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        setupRecyclerView();
        adapter = new ListaItemsTiendaAdapter(this, false);
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        puntosText = findViewById(R.id.puntosText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setupTienda();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clear();
    }

    @Override
    public void finCargando(ArrayList<ItemTienda> items, Integer misPuntos) {
        if (items.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        adapter.setItems(items);
        puntosText.setText(String.format("%s puntos.", String.valueOf(misPuntos)));
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClickItemTienda(ItemTienda itemTienda) {
        DialogComprarItemTienda dialogComprarItemTienda = new DialogComprarItemTienda();

        int misPuntos = presenter.getPuntos();
        dialogComprarItemTienda.setup(misPuntos, itemTienda);

        dialogComprarItemTienda.show(getFragmentManager(), "comprarItem");
    }

    @Override
    public void onItemComprado(ItemTienda itemTienda) {
        presenter.comprarItem(itemTienda);
        toastShort(this, getString(R.string.tu_compra_fue_un_exito));
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(espacioVerticalDecorator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
