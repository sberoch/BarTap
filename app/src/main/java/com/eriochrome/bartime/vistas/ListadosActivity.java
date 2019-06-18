package com.eriochrome.bartime.vistas;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.app.FragmentTransaction;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.adapters.JuegoHolder;
import com.eriochrome.bartime.contracts.ListadosContract;
import com.eriochrome.bartime.modelos.entidades.Juego;
import com.eriochrome.bartime.presenters.ListadosPresenter;
import com.eriochrome.bartime.vistas.dialogs.DialogCrearCuenta;
import com.eriochrome.bartime.vistas.dialogs.DialogResumenJuego;
import com.eriochrome.bartime.vistas.dialogs.DialogSeleccionFiltros;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadosActivity extends AppCompatActivity implements ListadosContract.View,
        DialogSeleccionFiltros.FiltrosListener,
        JuegoHolder.OnJuegoHolderClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        DialogResumenJuego.Listener,
        DialogCrearCuenta.Listener{

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;
    private ImageButton avisos;

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;

    private static final int RC_SIGN_IN = 1;

    private ListadosPresenter presenter;

    //private LocationHelper locationHelper;
    private Location ultimaUbicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        avisos = findViewById(R.id.avisos);
        navigationView = findViewById(R.id.nav_drawer);
        spinner = findViewById(R.id.spinner_listado);

        presenter = new ListadosPresenter();
        presenter.bind(this);

        /*locationHelper = new LocationHelper(this);
        locationHelper.checkpermission();
        if (locationHelper.checkPlayServices()) {
            locationHelper.buildGoogleApiClient();
        }*/
        setupListeners();
    }


    private void updateUI() {
        setupDrawer();
        setupSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        if (presenter.estaConectado()) {
            presenter.conectar();
            presenter.checkearAvisos();
        }
        //locationHelper.checkPlayServices();
    }

    private void ejecutarOpcionMenu(int id) {
        switch (id) {
            case R.id.iniciar_sesion:
                loginUsuario();
                break;

            case R.id.juegos:
                startActivity(new Intent(ListadosActivity.this, MisJuegosActivity.class));
                break;

            case R.id.compras:
                startActivity(new Intent(ListadosActivity.this, MisComprasActivity.class));
                break;

            case R.id.guardados:
                startFragment(new ListadoFavoritosFragment());
                spinner.setSelection(spinnerAdapter.getPosition("Mis Favoritos"));
                break;

            case R.id.contacto:
                startActivity(new Intent(ListadosActivity.this, ContactoActivity.class));
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
                break;
        }
    }

    private void loginUsuario() {
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.custom_login_ui)
                .setEmailButtonId(R.id.normal_login)
                .setGoogleButtonId(R.id.google_login)
                .build();

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAuthMethodPickerLayout(customLayout)
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.drawable.bar_time_2)
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
        } else {
            //locationHelper.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void setupListeners() {
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            ejecutarOpcionMenu(menuItem.getItemId());
            return false; //Devuelvo false para que no quede seleccionado.
        });
        avisos.setOnClickListener(v -> {
            if (presenter.estaConectado()) {
                startActivity(new Intent(ListadosActivity.this, AvisosActivity.class));
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startFragment(new ListadoBaresFragment());
                        break;

                    case 1:
                        startFragment(new ListadoJuegosFragment());
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
        ArrayList<String> listaFragments = new ArrayList<>();
        listaFragments.add(getString(R.string.bares));
        listaFragments.add(getString(R.string.juegos));
        if (presenter.estaConectado()) {
            listaFragments.add(getString(R.string.mis_favoritos));
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
            menu.findItem(R.id.juegos).setVisible(true);
            menu.findItem(R.id.compras).setVisible(true);
            menu.findItem(R.id.guardados).setVisible(true);
            menu.findItem(R.id.contacto).setVisible(true);
            menu.findItem(R.id.cerrar_sesion).setVisible(true);
            menu.findItem(R.id.salir).setVisible(false);
        } else {
            menu.findItem(R.id.iniciar_sesion).setVisible(true);
            menu.findItem(R.id.juegos).setVisible(false);
            menu.findItem(R.id.compras).setVisible(false);
            menu.findItem(R.id.guardados).setVisible(false);
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
    public void aplicarFiltros(AlertDialog dialog) {
        Fragment f = getFragmentManager().findFragmentById(R.id.fragment_container);
        //ultimaUbicacion = locationHelper.getLocation();
        if (f instanceof ListadoBaresFragment) {
            ((ListadoBaresFragment) f).aplicarFiltros(dialog, ultimaUbicacion);
        }
    }

    @Override
    public void onClickJuego(Juego juego) {
        Fragment f = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof ListadoJuegosFragment) {
            ((ListadoJuegosFragment) f).onClickJuego(juego);
        }
    }

    @Override
    public void login() {
        loginUsuario();
    }

    @Override
    public void hayAvisos() {
        avisos.setImageResource(R.drawable.ic_notifications_active_violet_24dp);
    }

    @Override
    public void noHayAvisos() {
        avisos.setImageResource(R.drawable.ic_notifications_none_violet_24dp);
    }

    @Override
    public void intentarParticiparDeJuego(Juego juego) {
        Fragment f = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof ListadoJuegosFragment) {
            ((ListadoJuegosFragment) f).intentarParticiparDeJuego(juego);
        }
    }

    @Override
    protected void onDestroy() {
        if (presenter.estaConectado()) {
            presenter.dejarDeCheckearAvisos();
        }
        presenter.unbind();
        super.onDestroy();
    }
}
