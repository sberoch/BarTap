package com.eriochrome.bartime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class ConfirmacionNuevoBar extends AppCompatActivity {

    private Button continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_nuevo_bar);

        continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> {
            startActivity(new Intent(ConfirmacionNuevoBar.this, ListadosActivity.class));
        });
    }
}
