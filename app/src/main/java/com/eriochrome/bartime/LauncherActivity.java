package com.eriochrome.bartime;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.eriochrome.bartime.modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class LauncherActivity extends AppCompatActivity {

    FirebaseAuth auth;
    DatabaseReference refUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    @Override
    protected void onStart() {
        super.onStart();

        refUsuarios = FirebaseDatabase.getInstance().getReference().child("usuarios");
        auth = FirebaseAuth.getInstance();

        redirigir();
    }

    /**
     * Usuario logeado a listados
     * Bar logeado a barControl
     * No logeado a DistincionDeUsuario
     */
    private void redirigir() {

        if (auth.getCurrentUser() != null) {
            String userID = auth.getCurrentUser().getUid();
            Query query = refUsuarios.child(userID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean esBar = dataSnapshot.getValue(Usuario.class).esBar();
                    if (esBar) {
                        //Es bar y esta logeado
                        startActivity(new Intent(LauncherActivity.this, BarControl.class));
                        overridePendingTransition(0,0);
                        finish();
                    } else {
                        //Es usuario y esta logeado
                        startActivity(new Intent(LauncherActivity.this, ListadosActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    toastShort(LauncherActivity.this, "Ocurrio un error. Revise su conexion a internet");
                }
            });
        }
        //No esta logeado
        else {
            startActivity(new Intent(this, DistincionDeUsuario.class));
            overridePendingTransition(0,0);
            finish();
        }
    }
}
