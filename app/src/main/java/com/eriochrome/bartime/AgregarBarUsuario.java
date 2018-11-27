package com.eriochrome.bartime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AgregarBarUsuario extends AppCompatActivity {

    private TextView volver;
    private Button listo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bar_usuario);

        volver = findViewById(R.id.volver);
        listo = findViewById(R.id.listo);

        volver.setOnClickListener(v -> {
            finish();
        });
        listo.setOnClickListener(v -> {
            //TODO: cambiar por la clase de confirmacion o lo que sea.
            Intent i = new Intent(AgregarBarUsuario.this, ListadoBares.class);
            startActivity(i);
        });
    }
}
