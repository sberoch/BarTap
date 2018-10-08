package com.eriochrome.bartime;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class Bar extends AppCompatActivity {

    private TextView barName;
    private Button meGusta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        setTitle("Pagina del Bar");

        barName = findViewById(R.id.bar_nombre);
        meGusta = findViewById(R.id.me_gusta);
        setTypefaces();
    }





    private void setTypefaces() {

        Typeface tfBold = Typeface.createFromAsset(getAssets(),"fonts/Lato-Bold.ttf");
        barName.setTypeface(tfBold);

        Typeface tfReg = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        meGusta.setTypeface(tfReg);
    }
}
