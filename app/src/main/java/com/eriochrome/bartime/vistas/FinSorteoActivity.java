package com.eriochrome.bartime.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.eriochrome.bartime.R;

public class FinSorteoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_sorteo);

        String ganador = getIntent().getStringExtra("ganador");
        TextView textoGanador = findViewById(R.id.texto_ganador);
        textoGanador.setText(String.format("%s ha ganado el sorteo.", ganador));

        Button continuar = findViewById(R.id.continuar);
        continuar.setOnClickListener(v -> {
            startActivity(new Intent(FinSorteoActivity.this, BarControlActivity.class));
            finish();
        });
    }
}
