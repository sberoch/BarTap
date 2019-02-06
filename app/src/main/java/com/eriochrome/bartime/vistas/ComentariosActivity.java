package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.ListaComentariosAdapter;
import com.eriochrome.bartime.contracts.ComentariosContract;
import com.eriochrome.bartime.modelos.entidades.Comentario;
import com.eriochrome.bartime.presenters.ComentariosPresenter;

import java.util.ArrayList;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ComentariosActivity extends AppCompatActivity implements ComentariosContract.View {

    private ComentariosPresenter presenter;

    private ProgressBar progressBar;
    private TextView back;

    private ListaComentariosAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        presenter = new ComentariosPresenter();
        presenter.bind(this);
        presenter.obtenerBar(getIntent());

        adapter = new ListaComentariosAdapter();
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.mostrarComentarios();
    }

    @Override
    public void cargando() {
        progressBar.setVisibility(View.VISIBLE);
        adapter.clear();
    }

    @Override
    public void finCargando(ArrayList<Comentario> comentarios) {
        if (comentarios.size() == 0) {
            toastShort(this, getString(R.string.no_hay_resultados));
        }
        adapter.setItems(comentarios);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

}
