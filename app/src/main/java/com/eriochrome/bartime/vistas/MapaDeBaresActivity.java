package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.MapaDeBaresContract;
import com.eriochrome.bartime.modelos.entidades.Bar;
import com.eriochrome.bartime.presenters.MapaDeBaresPresenter;
import com.eriochrome.bartime.utils.GlideApp;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.HashMap;

public class MapaDeBaresActivity extends AppCompatActivity implements
        MapaDeBaresContract.View,
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private static final int MIN_ESTRELLAS = 2;

    private MapaDeBaresPresenter presenter;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private RelativeLayout barRL;

    private final int DEFAULT_ZOOM = 12;
    private final float ACCENT_VIOLET_HUE = 285.15f;
    private final LatLng ubicacionDefault = new LatLng(-34.5916106,-58.4496007); //Bsas

    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private HashMap<Marker, Bar> marcadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_de_bares);

        marcadores = new HashMap<>();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        barRL = findViewById(R.id.bar_rl);
        barRL.setVisibility(View.GONE);

        presenter = new MapaDeBaresPresenter();
        presenter.bind(this);

        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), DEFAULT_ZOOM));
            }
            @Override
            public void onError(Status status) {
                Log.d("asds", "An error occurred: " + status);
            }
        });
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(8.0f);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionDefault, DEFAULT_ZOOM));
        presenter.getPosicionesDeBares();
    }

    //Viene el marker asincronicamente
    @Override
    public void marcarBar(Bar bar) {
        LatLng latLng = new LatLng(bar.getLat(), bar.getLng());
        Marker marcador = mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(ACCENT_VIOLET_HUE))
                .position(latLng)
                .title(bar.getNombre()));
        marcadores.put(marcador, bar);
    }

    //Todos los markers cargados
    @Override
    public void listo() {
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        View barView = View.inflate(MapaDeBaresActivity.this, R.layout.item_bar, null);
        barRL.removeAllViews();
        barView.setTag(marker.hashCode());
        ponerValoresAlView(marcadores.get(marker), barView);
        barRL.addView(barView);
        barRL.setVisibility(View.VISIBLE);
        return false;
    }

    private void ponerValoresAlView(Bar bar, View barView) {
        TextView nombre = barView.findViewById(R.id.nombre_bar);
        nombre.setText(bar.getNombre());

        TextView ubicacion = barView.findViewById(R.id.ubicacion_bar);
        ubicacion.setText(bar.getUbicacion());
        Typeface tfLight = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        ubicacion.setTypeface(tfLight);

        ImageView imagen = barView.findViewById(R.id.imagen_bar);
        String nombreBar = bar.getNombre().replaceAll(" ", "_");
        String imagePath = nombreBar + ".jpg";
        StorageReference imagenRef = storageReference.child("imagenes").child(imagePath);
        GlideApp.with(barView)
                .load(imagenRef).placeholder(R.drawable.placeholder)
                .into(imagen);


        TextView estrellas = barView.findViewById(R.id.estrellas);
        setEstrellas(bar, estrellas);

        barView.setOnClickListener(v -> {
            Intent intent = new Intent(MapaDeBaresActivity.this, PaginaBarActivity.class);
            intent.putExtra("bar", bar);
            startActivity(intent);
        });
    }

    private void setEstrellas(Bar bar, TextView estrellas) {
        //Para que el bar no quede mal, solo se muestran las estrellas si tiene una puntuacion
        // mayor a la minima.
        double estrellasDelBar = bar.getEstrellas();
        if (estrellasDelBar >= MIN_ESTRELLAS) {
            estrellas.setText(String.format("%.1f", estrellasDelBar));
        } else {
            estrellas.setText(" -- ");
        }
    }
}
