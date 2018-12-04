package com.eriochrome.bartime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.eriochrome.bartime.modelos.Bar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class BarActivity extends AppCompatActivity {

    private TextView nombreBar;
    private Button calificacionOk;
    private EditText calificacionEditText;

    private Bar bar;

    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference baresRef = ref.child("bares");
    /*
    private DatabaseReference conOfertasRef = ref.child("baresConOferta");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        Intent intent = getIntent();
        bar = (Bar) intent.getSerializableExtra("bar");

        nombreBar = findViewById(R.id.nombre_bar);
        calificacionEditText = findViewById(R.id.calificacion);
        calificacionOk = findViewById(R.id.calificacion_ok);

        nombreBar.setText(bar.getNombre());
        calificacionOk.setOnClickListener(view -> {
           int calificacion = Integer.parseInt(calificacionEditText.getText().toString());
           bar.actualizarEstrellas(calificacion);
           actualizarEstrellasFirebase();
           finish();
        });

    }


    private void actualizarEstrellasFirebase() {
        //TODO: agregar a bares con oferta tambien
        baresRef.child(bar.getNombre()).child("estrellas").setValue(bar.getEstrellas());
        baresRef.child(bar.getNombre()).child("calificacionesAcumuladas").setValue(bar.getCalificacionesAcumuladas());
        baresRef.child(bar.getNombre()).child("numeroDeCalificaciones").setValue(bar.getNumeroDeCalificaciones());
    }
}
