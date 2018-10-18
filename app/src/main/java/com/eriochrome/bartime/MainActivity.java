package com.eriochrome.bartime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.eriochrome.bartime.utils.CustomTypefaceSpan;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_promo:
                        Intent i = new Intent(MainActivity.this, Bar.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_descubrir:
                        return true;
                    case R.id.navigation_eventos:
                        EnviadorDeNotificaciones notif = new EnviadorDeNotificaciones(this);
                        notif.crearNotificacion();
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
