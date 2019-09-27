package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.presenters.BarControlPresenter;
import com.eriochrome.bartime.utils.MySliderView;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class BarControlActivity extends AppCompatActivity implements BarControlContract.View {

    private static final int NUMERO_SOLICITUD_GALERIA = 1;

    private BarControlPresenter presenter;

    private DrawerLayout drawerLayout;
    private ImageButton drawerButton;
    private NavigationView navigationView;

    private ProgressBar loading;
    private RelativeLayout sinBarRl;
    private Button sinBarButton;

    private RelativeLayout barControlRl;
    private SliderLayout sliderShow;
    private TextView nombreBar;
    private TextView descripcion;
    private Button editarBar;
    private Button juegos;
    private Button miTienda;
    private Button agregarFotos;
    private ImageButton avisos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_control);

        checkPrimeraVez();

        presenter = new BarControlPresenter();
        presenter.bind(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerButton = findViewById(R.id.drawer_button);
        navigationView = findViewById(R.id.nav_drawer);

        loading = findViewById(R.id.progressBar);

        sinBarRl = findViewById(R.id.sin_bar_rl);
        sinBarButton = findViewById(R.id.sin_bar_btn);

        barControlRl = findViewById(R.id.bar_control_rl);
        sliderShow = findViewById(R.id.slider);
        nombreBar = findViewById(R.id.nombre_bar);
        descripcion = findViewById(R.id.descripcion);
        editarBar = findViewById(R.id.editar_bar);
        juegos = findViewById(R.id.juegos);
        miTienda = findViewById(R.id.mi_tienda);
        agregarFotos = findViewById(R.id.agregar_fotos);
        avisos = findViewById(R.id.avisos);

        setupListeners();
    }

    private void checkPrimeraVez() {
        Thread t = new Thread(() -> {
            //  Initialize SharedPreferences
            SharedPreferences getPrefs = PreferenceManager
                    .getDefaultSharedPreferences(getBaseContext());
            //  Create a new boolean and preference and set it to true
            boolean isFirstStart = getPrefs.getBoolean("firstStartBar", true);
            //  If the activity has never started before...
            if (isFirstStart) {
                //  Launch app intro
                final Intent i = new Intent(BarControlActivity.this, IntroduccionBarActivity.class);
                runOnUiThread(() -> startActivity(i));
                //  Make a new preferences editor
                SharedPreferences.Editor e = getPrefs.edit();
                //  Edit preference to make it false because we don't want this to run again
                e.putBoolean("firstStartBar", false);
                //  Apply changes
                e.apply();
            }
        });

        t.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setupBar();
    }

    private void updateUI() {
        setupDrawer();
        if (presenter.hayBarAsociado()) {
            sinBarRl.setVisibility(View.GONE);
            barControlRl.setVisibility(View.VISIBLE);
            nombreBar.setText(presenter.getNombreBar());
            setupDescripcion();
            sliderShow.removeAllSliders();
            presenter.cargarImagenes();
        } else {
            sinBarRl.setVisibility(View.VISIBLE);
            barControlRl.setVisibility(View.GONE);
        }
    }

    private void setupDescripcion() {
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        descripcion.setTypeface(tf);
        String desc = presenter.getDescripcion();
        if (!desc.equals(""))
            descripcion.setText(desc);
        else
            descripcion.setText(getString(R.string.aun_sin_descripcion));
    }

    private void setupListeners() {
        sinBarButton.setOnClickListener(v -> {
            startActivity(new Intent(BarControlActivity.this, DatosBarPrincipalActivity.class));
            finish();
        });
        editarBar.setOnClickListener(v -> {
            Intent i = new Intent(BarControlActivity.this, DatosBarPrincipalActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
        juegos.setOnClickListener(v -> {
            Intent i = new Intent(BarControlActivity.this, JuegosGeneralActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
        miTienda.setOnClickListener(v -> {
            Intent i = new Intent(BarControlActivity.this, TiendaBarActivity.class);
            i = presenter.enviarBar(i);
            startActivity(i);
        });
        agregarFotos.setOnClickListener(v -> {
            sliderShow.stopAutoCycle();
            seleccionarImagenDeGaleria();
        });
        avisos.setOnClickListener(v ->
                startActivity(new Intent(BarControlActivity.this, AvisosBarActivity.class)));
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            drawerLayout.closeDrawers();
            ejecutarOpcionMenu(menuItem.getItemId());
            return false; //Devuelve false para que no quede seleccionado
        });
        drawerButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

    }

    private void ejecutarOpcionMenu(int itemId) {
        switch (itemId) {
            case R.id.contacto:
                startActivity(new Intent(this, ContactoActivity.class));
                break;

            case R.id.mis_ventas:
                if (presenter.hayBarAsociado()) {
                    Intent i = new Intent(this, ComprasBarActivity.class);
                    i = presenter.enviarBar(i);
                    startActivity(i);
                }
                break;

            case R.id.comentarios:
                if (presenter.hayBarAsociado()) {
                    Intent i = new Intent(this, ComentariosActivity.class);
                    i = presenter.enviarBar(i);
                    startActivity(i);
                }
                break;

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

    @Override
    public void hayAvisos() {
        avisos.setImageResource(R.drawable.ic_notifications_active_violet_24dp);
    }

    @Override
    public void noHayAvisos() {
        avisos.setImageResource(R.drawable.ic_notifications_none_violet_24dp);
    }

    @Override
    public void onImageLoaded(String path) {
        MySliderView sliderView = new MySliderView(this);
        sliderView.image(path)
                .setScaleType(BaseSliderView.ScaleType.CenterInside);
        sliderShow.addSlider(sliderView);
    }

    private void seleccionarImagenDeGaleria() {
        Intent elegirFotoIntent = new Intent(Intent.ACTION_PICK);
        elegirFotoIntent.setType("image/*");
        startActivityForResult(elegirFotoIntent, NUMERO_SOLICITUD_GALERIA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NUMERO_SOLICITUD_GALERIA) {
            if (resultCode == RESULT_OK) {
                Uri path;
                if (data != null) {
                    path = data.getData();
                    presenter.subirFoto(path);
                } else {
                    toastShort(BarControlActivity.this, getString(R.string.ocurrio_error_inesperado));
                }
            }
            else {
                toastShort(BarControlActivity.this, getString(R.string.no_elegiste_imagen));
            }
        }
        sliderShow.startAutoCycle();
    }

    @Override
    protected void onStop() {
        sliderShow.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        presenter.dejarDeCheckearAvisos();
        super.onDestroy();
    }
}
