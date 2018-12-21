package com.eriochrome.bartime;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.eriochrome.bartime.modelos.Usuario;
import com.eriochrome.bartime.modelos.UsuarioBar;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Arrays;
import java.util.List;

import static com.eriochrome.bartime.utils.Utils.toastShort;

public class DistincionDeUsuario extends AppCompatActivity {

    private Button tengoBar;
    private Button comenzar;
    private FirebaseAuth auth;
    private DatabaseReference refUsuarios;

    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distincion_de_usuario);

        tengoBar = findViewById(R.id.tengoBar);
        comenzar = findViewById(R.id.comenzar);

        refUsuarios = FirebaseDatabase.getInstance().getReference().child("usuarios");
        auth = FirebaseAuth.getInstance();

        setTypefaces();
        setListeners();
    }


    private void setListeners() {
        comenzar.setOnClickListener(view -> {
            startActivity(new Intent(DistincionDeUsuario.this, ListadosActivity.class));
            finish();
        });
        tengoBar.setOnClickListener(view -> loginBar());
    }


    private void setTypefaces() {
        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/Lato-Bold.ttf");
        tengoBar.setTypeface(tf);
        comenzar.setTypeface(tf);
    }


    private void loginBar() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                //Paso el usuario del auth a la database
                FirebaseUser barAuth = auth.getCurrentUser();
                Usuario barUsuario = new UsuarioBar(barAuth.getDisplayName());
                refUsuarios.child(barAuth.getUid()).setValue(barUsuario);

                //Entro a BarControl
                startActivity(new Intent(DistincionDeUsuario.this, BarControl.class));
                finish();
            } else {
                toastShort(DistincionDeUsuario.this, "Ocurrio un error. Intente nuevamente");
            }
        }
    }
}
