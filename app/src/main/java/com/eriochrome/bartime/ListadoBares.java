package com.eriochrome.bartime;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListadoBares extends AppCompatActivity {

    private ListView listView;
    private View footerView;
    private ListaBaresAdapter adapter;
    private ArrayList<Bar> listaBares;

    private DatabaseReference refBares;

    private ProgressBar loading;

    private EditText buscar;
    private Button filtrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_bares);

        buscar = findViewById(R.id.buscar);
        filtrar = findViewById(R.id.filtrar);

        refBares = FirebaseDatabase.getInstance().getReference().child("bares");

        listaBares = new ArrayList<>();
        listView = findViewById(R.id.listview);
        footerView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_no_encontras, null, false);
        listView.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE);
        adapter = new ListaBaresAdapter(listaBares, this);
        listView.setAdapter(adapter);

        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        footerView.setOnClickListener(view -> {
            Intent i = new Intent(ListadoBares.this, AgregarBarUsuario.class);
            startActivity(i);
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


        listaBares.clear();
        adapter.notifyDataSetChanged();
        mostrarCargando();

        refBares.orderByChild("estrellas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0) {
                    Toast.makeText(ListadoBares.this, "No se encontraron resultados.", Toast.LENGTH_SHORT).show();
                }
                for (DataSnapshot barSnapshot : dataSnapshot.getChildren()) {
                    Bar bar = barSnapshot.getValue(Bar.class);
                    listaBares.add(0, bar);
                }
                adapter.notifyDataSetChanged();
                ocultarCargando();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ListadoBares.this, "Error al leer base de datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void mostrarCargando() {
        loading.setVisibility(View.VISIBLE);
        footerView.setVisibility(View.INVISIBLE);
    }


    private void ocultarCargando() {
        loading.setVisibility(View.GONE);
        footerView.setVisibility(View.VISIBLE);
    }

}
