package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

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
    private ImageButton share;

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;

    private static final int RC_SIGN_IN = 1;

    private ListadosPresenter presenter;

    private Location ultimaUbicacion;
    private boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int CODIGO_REQUEST_LOCATION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        checkPrimeraVez();

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        avisos = findViewById(R.id.avisos);
        share = findViewById(R.id.share);
        navigationView = findViewById(R.id.nav_drawer);
        spinner = findViewById(R.id.spinner_listado);

        presenter = new ListadosPresenter();
        presenter.bind(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        setupListeners();
    }

    private void checkPrimeraVez() {
        Thread t = new Thread(() -> {
            //  Initialize SharedPreferences
            SharedPreferences getPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getBaseContext());
            //  Create a new boolean and preference and set it to true
            boolean isFirstStart = getPrefs.getBoolean("firstStartUser", true);
            //  If the activity has never started before...
            if (isFirstStart) {
                //  Launch app intro
                final Intent i = new Intent(ListadosActivity.this, IntroduccionUsuarioActivity.class);
                runOnUiThread(() -> startActivity(i));
                //  Make a new preferences editor
                SharedPreferences.Editor e = getPrefs.edit();
                //  Edit preference to make it false because we don't want this to run again
                e.putBoolean("firstStartUser", false);
                //  Apply changes
                e.apply();
            }
        });

        t.start();
    }


    private void updateUI() {
        setupDrawer();
        setupSpinner();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        if (presenter.estaConectado()) {
            presenter.conectar();
            presenter.checkearAvisos();
        }
        getLastLocation();
    }

    private void checkLocation() {
        if (!mLocationPermissionGranted) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLocationPermissionGranted = true;

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        CODIGO_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case CODIGO_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void getLastLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(task -> ultimaUbicacion = task.getResult());
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void ejecutarOpcionMenu(int id) {
        switch (id) {
            case R.id.iniciar_sesion:
                loginUsuario();
                break;

            case R.id.agregar_bar:
                startActivity(new Intent(ListadosActivity.this, AgregarBarUsuarioActivity.class));
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
                        .setLogo(R.drawable.bar_tap_2)
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
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
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
        share.setOnClickListener(v -> {
            presenter.mockCompartirConDynLink();
            /*Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

            String invitacion = getString(R.string.share_text);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, invitacion);

            String chooserText = getString(R.string.compartir);
            startActivity(Intent.createChooser(sharingIntent, chooserText));*/
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
            menu.findItem(R.id.agregar_bar).setVisible(true);
            menu.findItem(R.id.juegos).setVisible(true);
            menu.findItem(R.id.compras).setVisible(true);
            menu.findItem(R.id.guardados).setVisible(true);
            menu.findItem(R.id.contacto).setVisible(true);
            menu.findItem(R.id.cerrar_sesion).setVisible(true);
            menu.findItem(R.id.salir).setVisible(false);
        } else {
            menu.findItem(R.id.iniciar_sesion).setVisible(true);
            menu.findItem(R.id.agregar_bar).setVisible(false);
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
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof ListadoBaresFragment) {
            ((ListadoBaresFragment) f).aplicarFiltros(dialog, ultimaUbicacion);
        }
    }

    @Override
    public void onClickJuego(Juego juego) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
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
    public void setInvUrl(Uri shortLink) {
        //TODO: como sospechaba esto abre un link que no anda
        //TODO: ver como hacer para que ande cuando no hay pagina y ver que hacer con tema bartime/bartap
        Intent sharingIntent = new Intent(Intent.ACTION_SENDTO);
        sharingIntent.setData(Uri.parse("mailto:"));
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));

        String invitacion = "Link: " + shortLink.toString();
        sharingIntent.putExtra(Intent.EXTRA_TEXT, invitacion);

        if (sharingIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sharingIntent);
        }
    }

    @Override
    public void intentarParticiparDeJuego(Juego juego) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
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
