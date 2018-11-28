package com.eriochrome.bartime;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eriochrome.bartime.adapters.DrawerAdapter;
import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ListadoBares extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ImageButton drawerButton;
    private DrawerAdapter drawerAdapter;

    private ListView baresListView;
    private View footerView;
    private ListaBaresAdapter baresAdapter;
    private ArrayList<Bar> listaBares;

    private DatabaseReference refBares;
    private ProgressBar loading;

    //TODO: mock
    ArrayList<String> menulista = new ArrayList<>(Arrays.asList("Que","Onda","Gato","Piola"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_bares);

        refBares = FirebaseDatabase.getInstance().getReference().child("bares");

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerListView = findViewById(R.id.left_drawer);
        drawerAdapter = new DrawerAdapter(this, menulista);
        drawerListView.setAdapter(drawerAdapter);
        drawerListView.setOnItemClickListener(null);
        drawerButton = findViewById(R.id.drawer);

        listaBares = new ArrayList<>();
        baresListView = findViewById(R.id.listview);
        footerView = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.item_no_encontras, null, false);
        baresListView.addFooterView(footerView);
        footerView.setVisibility(View.INVISIBLE);
        baresAdapter = new ListaBaresAdapter(listaBares, this);
        baresListView.setAdapter(baresAdapter);

        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));

        footerView.setOnClickListener(view -> {
            Intent i = new Intent(ListadoBares.this, AgregarBarUsuario.class);
            startActivity(i);
        });

    }


    @Override
    protected void onStart() {
        super.onStart();


        listaBares.clear();
        baresAdapter.notifyDataSetChanged();
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
                baresAdapter.notifyDataSetChanged();
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
