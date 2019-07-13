package com.eriochrome.bartime.vistas;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.DatosBarReclamarContract;
import com.eriochrome.bartime.presenters.DatosBarReclamarPresenter;

public class DatosBarReclamarActivity extends AppCompatActivity implements DatosBarReclamarContract.View {

    private DatosBarReclamarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_bar_reclamar);

        presenter = new DatosBarReclamarPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
