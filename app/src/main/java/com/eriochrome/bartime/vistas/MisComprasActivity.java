package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.MisComprasContract;
import com.eriochrome.bartime.presenters.MisComprasPresenter;

public class MisComprasActivity extends AppCompatActivity implements MisComprasContract.View {

    private MisComprasPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_compras);
        //Recordar meterle el layout correspondiente

        presenter = new MisComprasPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
