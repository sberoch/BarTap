package com.eriochrome.bartime.vistas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.presenters.ListadosPresenter;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadosActivity extends AppCompatActivity implements ListadosContract.View, SeleccionFiltros.FiltrosListener {

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;

    private static final int RC_SIGN_IN = 1;

    private ListadosPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_drawer);
        spinner = findViewById(R.id.spinner_listado);

        presenter = new ListadosPresenter();
        presenter.bind(this);

        setupDrawer();
        setupSpinner();
        setupListeners();
        updateUI();
    }

    private void updateUI() {
        setupDrawer();
        setupSpinner();
    }


    private void ejecutarOpcionMenu(int id) {
        //TODO: mock
        switch (id) {
            case R.id.iniciar_sesion:
                loginUsuario();
                break;

            case R.id.perfil:
                toastShort(ListadosActivity.this, "Mi Perfil");
                break;

            case R.id.guardados:
                startFragment(new ListadoFavoritosFragment());
                spinner.setSelection(spinnerAdapter.getPosition("Mis Favoritos"));
                break;

            case R.id.contacto:
                startActivity(new Intent(ListadosActivity.this, ContactoActivity.class));
                finish();
                break;

            case R.id.ajustes:
                toastShort(ListadosActivity.this, "Ajustes");
                break;

            case R.id.cerrar_sesion:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(task -> {
                           startActivity(new Intent(ListadosActivity.this, DistincionDeUsuarioActivity.class));
                           finish();
                        });
                break;

            case R.id.salir:
                finishAndRemoveTask();
        }
    }

    private void loginUsuario() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                presenter.conectar();
                if (presenter.esNuevoUsuario()) {
                    presenter.subirUsuarioADatabase();
                }
                updateUI();

            } else {
                toastShort(ListadosActivity.this, "Ocurrio un error. Intente nuevamente");
            }
        }
    }


    private void setupListeners() {
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            ejecutarOpcionMenu(menuItem.getItemId());
            return true;
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startFragment(new ListadoBaresFragment());
                        break;

                    case 1:
                        startFragment(new ListadoDesafiosFragment());

                        break;

                    case 2:
                        startFragment(new ListadoFavoritosFragment());
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void setupSpinner() {
        //TODO: hacer un adapter custom para estos fragments, para abrirlos on click
        ArrayList<String> listaFragments = new ArrayList<>();
        listaFragments.add("Bares");
        listaFragments.add("Desafios");
        if(presenter.estaConectado()) {
            listaFragments.add("Mis Favoritos");
        }
        spinnerAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_text, listaFragments);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        spinner.setAdapter(spinnerAdapter);
    }


    private void startFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    private void setupDrawer() {
        //Header
        View header = navigationView.getHeaderView(0);
        TextView usuarioActivo = header.findViewById(R.id.usuario_activo);
        usuarioActivo.setText(presenter.getNombreUsuario());

        //Opciones
        Menu menu = navigationView.getMenu();
        setupItems(menu, presenter.estaConectado());
    }


    private void setupItems(Menu menu, boolean conectado) {
        if (conectado) {
            menu.findItem(R.id.iniciar_sesion).setVisible(false);
            menu.findItem(R.id.perfil).setVisible(true);
            menu.findItem(R.id.guardados).setVisible(true);
            menu.findItem(R.id.ajustes).setVisible(true);
            menu.findItem(R.id.contacto).setVisible(true);
            menu.findItem(R.id.cerrar_sesion).setVisible(true);
            menu.findItem(R.id.salir).setVisible(false);
        } else {
            menu.findItem(R.id.iniciar_sesion).setVisible(true);
            menu.findItem(R.id.perfil).setVisible(false);
            menu.findItem(R.id.guardados).setVisible(false);
            menu.findItem(R.id.ajustes).setVisible(false);
            menu.findItem(R.id.contacto).setVisible(false);
            menu.findItem(R.id.cerrar_sesion).setVisible(false);
            menu.findItem(R.id.salir).setVisible(true);
        }
    }


    @Override
    public void onBackPressed() {
        finishAndRemoveTask();
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void aplicarFiltros(AlertDialog dialog) {
        Fragment f = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof ListadoBaresFragment) {
            ((ListadoBaresFragment) f).aplicarFiltros(dialog);
        }
    }
}
