package com.eriochrome.bartime.vistas;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarContract;
import com.eriochrome.bartime.presenters.DatosBarPresenter;
import com.eriochrome.bartime.utils.FragmentChangeListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DatosBarActivity extends AppCompatActivity implements
        DatosBarContract.View,
        FragmentChangeListener,
        DatosBarPrincipalFragment.Listener,
        DatosBarHorariosFragment.OnReady,
        DatosBarOpcionalesFragment.OnReady{

    private DatosBarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_owner);

        presenter = new DatosBarPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        replaceFragment(new DatosBarPrincipalFragment());
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onReadyPpal(String nombreBar, String desc, String ubicacion) {
        presenter.setNombre(nombreBar);
        presenter.setDesc(desc);
        presenter.setUbicacion(ubicacion);
    }

    @Override
    public void onReadyHorarios(HashMap<String, Integer> horariosInicial, HashMap<String, Integer> horariosFinal) {
        presenter.setHorarios(horariosInicial, horariosFinal);
    }

    @Override
    public void onReadyHappyHour(HashMap<String, Integer> hhInicial, HashMap<String, Integer> hhFinal) {
        presenter.setHappyHour(hhInicial, hhFinal);
    }

    @Override
    public void onReadyMetodosPago(ArrayList<String> metodosDePago) {
        presenter.setMetodosPago(metodosDePago);
    }
}
