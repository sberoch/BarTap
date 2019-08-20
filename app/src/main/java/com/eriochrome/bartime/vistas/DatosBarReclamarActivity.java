package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.BarReclamarHolder;
import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaBaresAReclamarAdapter;
import com.eriochrome.bartime.contracts.DatosBarReclamarContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.presenters.DatosBarReclamarPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogReclamarBar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class DatosBarReclamarActivity extends AppCompatActivity implements
        DatosBarReclamarContract.View,
        BarReclamarHolder.Listener,
        DialogReclamarBar.Listener {

    private DatosBarReclamarPresenter presenter;
    private ProgressBar progressBar;

    private RecyclerView recyclerView;
    private ListaBaresAReclamarAdapter adapter;

    private Button noDeseoReclamar;
    private TextView aclaracion1;
    private TextView aclaracion2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_reclamar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new ListaBaresAReclamarAdapter(this);
        recyclerView.setAdapter(adapter);
        setupRecyclerView();

        aclaracion1 = findViewById(R.id.acl_1);
        aclaracion1.setVisibility(View.GONE);
        aclaracion2 = findViewById(R.id.acl_2);
        aclaracion2.setVisibility(View.GONE);
        noDeseoReclamar = findViewById(R.id.no_deseo_reclamar);
        noDeseoReclamar.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        presenter = new DatosBarReclamarPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        noDeseoReclamar.setOnClickListener(v -> {
            seguir();
        });

        presenter.mostrarBaresParaReclamar();
    }

    private void seguir() {
        Intent i = new Intent(DatosBarReclamarActivity.this, DatosBarHorariosActivity.class);
        i = presenter.enviarBar(i);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(espacioVerticalDecorator);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void onClickBar(Bar bar) {
        DialogReclamarBar dialogReclamarBar = new DialogReclamarBar();
        dialogReclamarBar.setBar(bar);
        dialogReclamarBar.show(getSupportFragmentManager(), "reclamar");
    }

    @Override
    public void reclamarBar(Bar bar) {
        presenter.reclamarBar(bar);
        seguir();
    }

    @Override
    public void cargando() {
        adapter.clear();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void finCargando(ArrayList<Bar> nuevosBares) {
        if (nuevosBares.isEmpty()) {
            seguir();
        }
        adapter.setItems(nuevosBares);
        progressBar.setVisibility(View.GONE);
        aclaracion1.setVisibility(View.VISIBLE);
        aclaracion2.setVisibility(View.VISIBLE);
        noDeseoReclamar.setVisibility(View.VISIBLE);
    }
}
