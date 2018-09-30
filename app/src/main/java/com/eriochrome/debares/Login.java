package com.eriochrome.debares;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");

        Button entrar = findViewById(R.id.entrar);
        entrar.setTypeface(tf);

        Button crearCuenta = findViewById(R.id.crearCuenta);
        crearCuenta.setTypeface(tf);

        EditText usuario = findViewById(R.id.userEditText);
        usuario.setTypeface(tf);

        EditText pass = findViewById(R.id.passEditText);
        pass.setTypeface(tf);

    }
}
