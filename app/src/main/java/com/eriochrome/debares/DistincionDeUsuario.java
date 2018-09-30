package com.eriochrome.debares;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DistincionDeUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distincion_de_usuario);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");

        Button tengoBar = findViewById(R.id.tengoBar);
        tengoBar.setTypeface(tf);

        tengoBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DistincionDeUsuario.this, Login.class);
                startActivity(i);
            }
        });

        Button buscoBar = findViewById(R.id.buscoBar);
        buscoBar.setTypeface(tf);

    }
}
