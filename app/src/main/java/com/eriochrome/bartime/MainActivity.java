package com.eriochrome.bartime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.eriochrome.bartime.utils.CustomTypefaceSpan;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListaBaresAdapter adapter;

    private ArrayList<Bar> listaBares;

    //TODO: mock
    private static final String DESCRIPCION_MOCK = "Descripcion breve del bar para que el usuario pueda ver...";

    private String title1 = "Bar de prueba";
    private String subtitle1 = "¡OFERTA! Cerveza 2X1 solo por la proxima hora! Que estas esperando?";

    private String title2 = "Mi Bar";
    private String subtitle2 = "¡DESAFIO! Tiempo Restante 0:59:59 - Sacarse una foto en...";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_promo:
                        EnviadorDeNotificaciones notif2 = new EnviadorDeNotificaciones(this);
                        notif2.crearNotificacion(title2, subtitle2);
                        notif2.enviarNotificacion();
                        return true;
                    case R.id.navigation_descubrir:
                        return true;
                    case R.id.navigation_eventos:
                        EnviadorDeNotificaciones notif = new EnviadorDeNotificaciones(this);
                        notif.crearNotificacion(title1, subtitle1);
                        notif.enviarNotificacion();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();

        //Todo: mock
        listaBares = new ArrayList<>();
        Bar bar1 = new Bar("Bar de prueba 1", DESCRIPCION_MOCK, 1, 175);
        listaBares.add(bar1);

        Bar bar2 = new Bar("Bar de prueba 2", DESCRIPCION_MOCK, 2, 9);
        listaBares.add(bar2);

        Bar bar3 = new Bar("Bar de prueba 3", DESCRIPCION_MOCK, 3, 2003);
        listaBares.add(bar3);

        Bar bar4 = new Bar("Bar de prueba 4", DESCRIPCION_MOCK, 4, 54);
        listaBares.add(bar4);

        Bar bar5 = new Bar("Bar de prueba 5", DESCRIPCION_MOCK, 5, 17);
        listaBares.add(bar5);


        listView = findViewById(R.id.listview);
        adapter = new ListaBaresAdapter(listaBares, this);
        listView.setAdapter(adapter);

    }



    private void setupNavigation() {

        BottomNavigationView navigation = findViewById(R.id.navegacion);
        Menu menu = navigation.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            setTypefaceMenuItem(item);
        }
        navigation.setSelectedItemId(R.id.navigation_descubrir);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void setTypefaceMenuItem(MenuItem item) {

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Lato-Light.ttf");
        SpannableString newTitle = new SpannableString(item.getTitle());
        newTitle.setSpan(new CustomTypefaceSpan("", tf), 0, newTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        item.setTitle(newTitle);
    }

}
