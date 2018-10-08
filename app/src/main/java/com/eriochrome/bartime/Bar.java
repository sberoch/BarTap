package com.eriochrome.bartime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Bar extends AppCompatActivity {

    private TextView barName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        setTitle("Pagina del Bar");

        barName = findViewById(R.id.bar_nombre);
        setTypefaces();
    }





    private void setTypefaces() {

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        barName.setTypeface(tf);
    }
}
