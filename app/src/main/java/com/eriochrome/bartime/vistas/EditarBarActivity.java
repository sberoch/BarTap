package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.EditarBarContract;
import com.eriochrome.bartime.presenters.EditarBarPresenter;

public class EditarBarActivity extends AppCompatActivity implements EditarBarContract.View {

    private EditarBarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Recordar meterle el layout correspondiente

        presenter = new EditarBarPresenter();
        presenter.bind(this);
    }

    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
