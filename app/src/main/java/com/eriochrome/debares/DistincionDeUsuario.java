package com.eriochrome.debares;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class DistincionDeUsuario extends AppCompatActivity {

    private Button tengoBar;
    private Button buscoBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distincion_de_usuario);

        tengoBar = findViewById(R.id.tengoBar);
        buscoBar = findViewById(R.id.buscoBar);

        setTypefaces();
        setListeners();
    }



    private void setListeners() {

        tengoBar.setOnClickListener(view -> {
            Intent i = new Intent(DistincionDeUsuario.this, Login.class);
            startActivity(i);
        });
    }


    private void setTypefaces() {

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        tengoBar.setTypeface(tf);
        buscoBar.setTypeface(tf);
    }
}
