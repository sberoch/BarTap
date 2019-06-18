package com.eriochrome.bartime.vistas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.eriochrome.bartime.R;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class ContactoActivity extends AppCompatActivity {

    private Button contacto;
    private ImageButton volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        contacto = findViewById(R.id.contacto_button);
        contacto.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"appbartime@gmail.com"});
            try {
                startActivity(Intent.createChooser(i, "Enviar mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                toastShort(ContactoActivity.this, "No hay servicios de mail instalados en tu telefono.");
            }
        });

        volver = findViewById(R.id.volver);
        volver.setOnClickListener(v -> finish());
    }
}
