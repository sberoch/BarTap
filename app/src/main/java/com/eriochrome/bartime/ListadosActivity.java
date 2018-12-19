package com.eriochrome.bartime;

import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadosActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;

    private DatabaseReference refGlobal;

    private ProgressBar loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_bares);

        refGlobal = FirebaseDatabase.getInstance().getReference();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_drawer);

        loading = findViewById(R.id.progressBar);
        loading.setVisibility(View.GONE);

        setupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Fragment baresFragment = new ListadoBaresFragment();
        ((ListadoBaresFragment) baresFragment).setReferenciaADatabase(refGlobal);
        startFragment(baresFragment);
    }


    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void ejecutarOpcionMenu(int id) {
        //TODO: mock
        switch (id) {
            case R.id.perfil:
                toastShort(ListadosActivity.this, "Mi Perfil");
                break;

            case R.id.guardados:
                toastShort(ListadosActivity.this, "Guardados");
                break;

            case R.id.contacto:
                toastShort(ListadosActivity.this, "Contacto");
                break;

            case R.id.ajustes:
                toastShort(ListadosActivity.this, "Ajustes");
                break;

            case R.id.cerrar_sesion:
                toastShort(ListadosActivity.this, "Cerrar Sesion");
                break;
        }
    }


    private void setupListeners() {
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            ejecutarOpcionMenu(menuItem.getItemId());
            return true;
        });
    }


    private void cargando() {
        loading.setVisibility(View.VISIBLE);
    }


    private void finCargando() {
        loading.setVisibility(View.GONE);
    }
}
