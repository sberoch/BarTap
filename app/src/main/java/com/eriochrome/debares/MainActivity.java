package com.eriochrome.debares;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.asd);
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        textView.setTypeface(tf);
    }
}
