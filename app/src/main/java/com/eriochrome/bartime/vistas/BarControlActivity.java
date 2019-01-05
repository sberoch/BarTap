package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.AgregarBarOwnerContract;
import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.modelos.Bar;
import com.eriochrome.bartime.presenters.BarControlPresenter;
import com.firebase.ui.auth.AuthUI;

public class BarControlActivity extends AppCompatActivity implements BarControlContract.View {

    private BarControlPresenter presenter;

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;

    private ProgressBar loading;
    private RelativeLayout sinBarRl;
    private Button sinBarButton;

    private RelativeLayout barControlRl;
    private TextView nombreBar;
    private ImageButton editarBar;
    private Button crearDesafio;
    private Button crearOferta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_control);

        presenter = new BarControlPresenter();
        presenter.bind(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_drawer);

        loading = findViewById(R.id.progressBar);

        sinBarRl = findViewById(R.id.sin_bar_rl);
        sinBarButton = findViewById(R.id.sin_bar_btn);

        barControlRl = findViewById(R.id.bar_control_rl);
        nombreBar = findViewById(R.id.nombre_bar);
        editarBar = findViewById(R.id.editar_bar);
        crearDesafio = findViewById(R.id.crear_desafio);
        crearOferta = findViewById(R.id.crear_oferta);

        setupListeners();
    }


    @Override
    protected void onStart() {
        super.onStart();
        presenter.setupBar();
    }

    private void updateUI() {
        if (presenter.hayBarAsociado()) {
            sinBarRl.setVisibility(View.GONE);
            barControlRl.setVisibility(View.VISIBLE);
            nombreBar.setText(presenter.getNombreBar());
            setupDrawer();
        } else {
            sinBarRl.setVisibility(View.VISIBLE);
            barControlRl.setVisibility(View.GONE);
            navigationView.getHeaderView(0).setVisibility(View.GONE);

        }
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    private void setupListeners() {
        sinBarButton.setOnClickListener(v -> {
            startActivity(new Intent(BarControlActivity.this, AgregarBarOwnerActivity.class));
            finish();
        });
        editarBar.setOnClickListener(v -> {
            Intent i = new Intent(BarControlActivity.this, EditarBarActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
        crearDesafio.setOnClickListener(v -> {
            presenter.crearDesafio();
        });
        crearOferta.setOnClickListener(v -> {
            presenter.crearOferta();
        });
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            ejecutarOpcionMenu(menuItem.getItemId());
            return true;
        });
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.START));

    }

    private void ejecutarOpcionMenu(int itemId) {
        switch (itemId) {
            case R.id.cerrar_sesion:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(task -> {
                            startActivity(new Intent(BarControlActivity.this, DistincionDeUsuarioActivity.class));
                            finish();
                        });
                break;

            case R.id.salir:
                finishAndRemoveTask();
                break;
        }
    }

    private void setupDrawer() {
        View header = navigationView.getHeaderView(0);
        TextView usuarioActivo = header.findViewById(R.id.usuario_activo);
        usuarioActivo.setText(presenter.getNombreUsuario());

    }

    @Override
    public void cargando() {
        sinBarRl.setVisibility(View.GONE);
        barControlRl.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void finCargando() {
        loading.setVisibility(View.GONE);
        updateUI();
    }

}
