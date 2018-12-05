package com.eriochrome.bartime;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.eriochrome.bartime.adapters.EspacioVerticalDecorator;
import com.eriochrome.bartime.adapters.ListaBaresAdapter;
import com.eriochrome.bartime.adapters.SombraEspacioVerticalDecorator;
import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadoBares extends AppCompatActivity implements SeleccionFiltros.FiltrosListener {

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;

    private Button filtrar;
    private EditText buscar;

    private RecyclerView baresRecyclerView;
    private ListaBaresAdapter baresAdapter;
    private ArrayList<Bar> listaBares;

    private DatabaseReference refGlobal;
    private DatabaseReference refBares;
    private boolean ordenDescendente;

    private ProgressBar loading;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_bares);

        refGlobal = FirebaseDatabase.getInstance().getReference();
        refBares = refGlobal.child("bares");
        ordenDescendente = false;

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_drawer);

        filtrar = findViewById(R.id.filtrar);
        buscar = findViewById(R.id.buscar);

        listaBares = new ArrayList<>();
        baresRecyclerView = findViewById(R.id.recycler_view);

        baresAdapter = new ListaBaresAdapter(this, listaBares);
        baresRecyclerView.setHasFixedSize(true);
        setupRecyclerView();
        baresRecyclerView.setAdapter(baresAdapter);

        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        setupListeners();
    }


    @Override
    protected void onStart() {
        super.onStart();

        cargando();
        refBares.orderByChild("estrellas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot barSnapshot : dataSnapshot.getChildren()) {
                    Bar bar = barSnapshot.getValue(Bar.class);
                    listaBares.add(0, bar);
                }
                finCargando(listaBares.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ListadoBares.this, "Error al leer base de datos.", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void mostrarFiltros() {
        DialogFragment filtros = new SeleccionFiltros();
        filtros.show(getSupportFragmentManager(), "filtros");
    }


    private void cargando() {
        loading.setVisibility(View.VISIBLE);
        listaBares.clear();
        baresAdapter.notifyDataSetChanged();
    }


    private void finCargando(long cantResultados) {
        if (cantResultados == 0) {
            toastShort(this, "No hay resultados");
        }
        loading.setVisibility(View.GONE);
        baresAdapter.notifyDataSetChanged();
    }


    private void buscarBarConPalabra(String s) {
        cargando();
        final String busqueda = s.toLowerCase();
        refBares.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaBares.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String nombreBar = ds.child("nombre").getValue(String.class).toLowerCase();
                    if (nombreBar.contains(busqueda)) {
                        listaBares.add(ds.getValue(Bar.class));
                    }
                }
                finCargando(listaBares.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                toastShort(ListadoBares.this, "Ocurrio un error inesperado");
            }
        });
    }


    private void ejecutarOpcionMenu(int id) {
        //TODO: mock
        switch (id) {
            case R.id.perfil:
                toastShort(ListadoBares.this, "Mi Perfil");
                break;

            case R.id.guardados:
                toastShort(ListadoBares.this, "Guardados");
                break;

            case R.id.contacto:
                toastShort(ListadoBares.this, "Contacto");
                break;

            case R.id.ajustes:
                toastShort(ListadoBares.this, "Ajustes");
                break;

            case R.id.cerrar_sesion:
                toastShort(ListadoBares.this, "Cerrar Sesion");
                break;
        }
    }



    @Override
    public void aplicarFiltros(DialogFragment dialogFragment, AlertDialog dialog) {
       cargando();
       Query query = obtenerQuery(dialog);
       query.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for (DataSnapshot barSnap : dataSnapshot.getChildren()) {
                      listaBares.add(barSnap.getValue(Bar.class));
              }
              if(ordenDescendente) {
                  Collections.reverse(listaBares );
              }
              finCargando(listaBares.size());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {
              toastShort(ListadoBares.this, "Ocurrio un error inesperado");
              finish();
          }
       });
    }


    /**
     * Achica el query segun los datos marcados en el dialog.
     *
     * NOTAR: hay bares que estan en otra rama del json en firebase para poder combinar filtros.
     */
    private Query obtenerQuery(AlertDialog dialog) {
        ordenDescendente = false;
        Query query = refBares;
        Switch hayOfertas = dialog.findViewById(R.id.descuentos);
        RadioGroup ordenamientos = dialog.findViewById(R.id.ordenar_group);
        //Filtro: bares en oferta
        if(hayOfertas.isChecked()) {
            query = refGlobal.child("baresConOferta");
        }
        //Ordeno segun lo elegido
        switch (ordenamientos.getCheckedRadioButtonId()) {
            case R.id.distancia:
                //TODO: distancias (geoloc)
                query = query.orderByChild("estrellas");
                toastShort(this, "Ojota, es por estrellas, arreglar.");
                break;

            case R.id.estrellas:
                query = query.orderByChild("estrellas");
                ordenDescendente = true;
                break;

            case R.id.nombre:
                query = query.orderByChild("nombre");
                break;
        }
        return query;
    }


    private void ocultarTeclado(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }


    private void setupListeners() {
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            ejecutarOpcionMenu(menuItem.getItemId());
            return true;
        });
        filtrar.setOnClickListener(v -> mostrarFiltros());
        buscar.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                buscarBarConPalabra(buscar.getText().toString());
                ocultarTeclado();
                return true;
            }
            return false;
        });
    }


    private void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        int espacioVertical = 30;
        EspacioVerticalDecorator espacioVerticalDecorator = new EspacioVerticalDecorator(espacioVertical);
        SombraEspacioVerticalDecorator sombra = new SombraEspacioVerticalDecorator(this, R.drawable.drop_shadow);

        baresRecyclerView.setLayoutManager(layoutManager);
        baresRecyclerView.addItemDecoration(espacioVerticalDecorator);
        baresRecyclerView.addItemDecoration(sombra);
    }
}
