package com.eriochrome.bartime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class DistincionDeUsuario extends AppCompatActivity {

    private Button tengoBar;
    private Button comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distincion_de_usuario);

        tengoBar = findViewById(R.id.tengoBar);
        comenzar = findViewById(R.id.comenzar);
        setTypefaces();
        setListeners();
    }



    private void setListeners() {

        comenzar.setOnClickListener(view -> {
            Intent i = new Intent(DistincionDeUsuario.this, ListadosActivity.class);
            startActivity(i);
        });
    }


    private void setTypefaces() {

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Bold.ttf");
        tengoBar.setTypeface(tf);
        comenzar.setTypeface(tf);
    }
}
