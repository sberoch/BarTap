package com.eriochrome.bartime;

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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.eriochrome.bartime.modelos.Usuario;
import com.eriochrome.bartime.modelos.UsuarioAnonimo;
import com.eriochrome.bartime.modelos.UsuarioRegistrado;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ListadosActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;
    private Spinner spinner;

    private DatabaseReference refGlobal;
    private DatabaseReference refUsuarios;
    private FirebaseAuth auth;
    private static final int RC_SIGN_IN = 1;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        refGlobal = FirebaseDatabase.getInstance().getReference();
        refUsuarios = refGlobal.child("usuarios");
        auth = FirebaseAuth.getInstance();

        if (estaConectado()) {
            usuario = UsuarioRegistrado.crearConAuth(auth.getCurrentUser());
        } else {
            usuario = new UsuarioAnonimo();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_drawer);
        spinner = findViewById(R.id.spinner_listado);

        setupDrawer();
        setupSpinner();
        setupListeners();
        updateUI();
    }


    private void updateUI() {
        setupDrawer();
    }


    @Override
    protected void onStart() {
        super.onStart();
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
                toastShort(ListadosActivity.this, "Guardados");
                break;

            case R.id.contacto:
                toastShort(ListadosActivity.this, "Contacto");
                break;

            case R.id.ajustes:
                toastShort(ListadosActivity.this, "Ajustes");
                break;

            case R.id.cerrar_sesion:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(task -> {
                           startActivity(new Intent(ListadosActivity.this, DistincionDeUsuario.class));
                           finish();
                        });
                break;
        }
    }


    private boolean estaConectado() {
        return auth.getCurrentUser() != null;
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
                //Paso el usuario del auth a la database
                FirebaseUser usuarioAuth = auth.getCurrentUser();
                usuario = UsuarioRegistrado.crearConAuth(usuarioAuth);
                refUsuarios.child(usuarioAuth.getUid()).setValue(usuario);

                //Actualizo UI
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
                        Fragment baresFragment = new ListadoBaresFragment();
                        ((ListadoBaresFragment) baresFragment).setReferenciaADatabase(refGlobal);
                        startFragment(baresFragment);
                        break;

                    case 1:
                        Fragment desafiosFragment = new ListadoDesafiosFragment();
                        startFragment(desafiosFragment);
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
        ArrayAdapter<String> adapterFragments = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, listaFragments);
        adapterFragments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterFragments);
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
        usuarioActivo.setText(usuario.getNombre());

        //Opciones
        Menu menu = navigationView.getMenu();
        setupItems(menu, estaConectado());
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
}
