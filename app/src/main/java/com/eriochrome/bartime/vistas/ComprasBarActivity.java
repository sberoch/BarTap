package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.ComprobanteCompraHolder;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaComprasAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.contracts.ComprasBarContract;
import com.eriochrome.bartime.modelos.entidades.ComprobanteDeCompra;
import com.eriochrome.bartime.presenters.ComprasBarPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogValidarCompra;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ComprasBarActivity extends AppCompatActivity implements
        ComprasBarContract.View,
        ComprobanteCompraHolder.OnComprobanteClickListener,
        DialogValidarCompra.OnValidarListener{

    private ComprasBarPresenter presenter;

    private ProgressBar progressBar;

    private RecyclerView comprasRecyclerView;
    private ListaComprasAdapter comprasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras_bar);

        presenter = new ComprasBarPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        comprasRecyclerView = findViewById(R.id.recyclerView);
        comprasRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        comprasAdapter = new ListaComprasAdapter(this, true);
        comprasRecyclerView.setAdapter(comprasAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.cargarCompras();
    }

    @Override
    public void onClick(ComprobanteDeCompra comprobanteDeCompra) {
        DialogValidarCompra dialogValidarCompra = new DialogValidarCompra();
        dialogValidarCompra.setComprobante(comprobanteDeCompra);
        dialogValidarCompra.show(getFragmentManager(), "validarCompra");
    }

    @Override
    public void onValidar(ComprobanteDeCompra comprobanteDeCompra) {
        presenter.eliminarComprobante(comprobanteDeCompra);
        finish(); //Esto se puede hacer mejor que solo salir.
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
}
