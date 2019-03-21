package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaComprasAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.MisComprasContract;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;
import com.eriochrome.bartime.presenters.MisComprasPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class MisComprasActivity extends AppCompatActivity implements MisComprasContract.View {

    private MisComprasPresenter presenter;

    private ProgressBar progressBar;

    private RecyclerView comprasRecyclerView;
    private ListaComprasAdapter comprasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compras);

        presenter = new MisComprasPresenter();
        presenter.bind(this);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        comprasRecyclerView = findViewById(R.id.recyclerView);
        comprasRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        comprasAdapter = new ListaComprasAdapter(this, false);
        comprasRecyclerView.setAdapter(comprasAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarCompras();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        SombraEspacioVerticalDecorator sombra = new SombraEspacioVerticalDecorator(this, R.drawable.drop_shadow);

        comprasRecyclerView.setLayoutManager(layoutManager);
        comprasRecyclerView.addItemDecoration(espacioVerticalDecorator);
        comprasRecyclerView.addItemDecoration(sombra);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        comprasAdapter.clear();
    }

    @Override
    public void finCargando(ArrayList<ComprobanteDeCompra> compras) {
        if (compras.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        comprasAdapter.setItems(compras);
        progressBar.setVisibility(View.GONE);
    }
}
