package com.eriochrome.debares;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private Button entrar;
    private Button crearCuenta;
    private EditText usuario;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        entrar = findViewById(R.id.entrar);
        crearCuenta = findViewById(R.id.crearCuenta);
        usuario = findViewById(R.id.userEditText);
        pass = findViewById(R.id.passEditText);

        setTypefaces();
        setListeners();

    }



    private void setTypefaces() {

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Light.ttf");
        entrar.setTypeface(tf);
        crearCuenta.setTypeface(tf);
        usuario.setTypeface(tf);
        pass.setTypeface(tf);
    }


    private void setListeners() {

        entrar.setOnClickListener(view -> {
            Intent i = new Intent(Login.this,  MainActivity.class);
            startActivity(i);
        });
    }
}
