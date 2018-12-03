package com.eriochrome.bartime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.eriochrome.bartime.modelos.Bar;

public class BarActivity extends AppCompatActivity {

    private TextView nombreBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        Intent intent = getIntent();
        Bar bar = (Bar) intent.getSerializableExtra("bar");

        nombreBar = findViewById(R.id.nombre_bar);
        nombreBar.setText(bar.getNombre());

    }
}
