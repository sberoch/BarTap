package com.eriochrome.bartime.vistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.eriochrome.bartime.R;
import com.eriochrome.bartime.contracts.BarControlContract;
import com.eriochrome.bartime.presenters.BarControlPresenter;

public class BarControlActivity extends AppCompatActivity implements BarControlContract.View {

    private BarControlPresenter presenter;

    private RelativeLayout sinBarRl;
    private Button sinBarButton;

    private RelativeLayout barControlRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_control);

        presenter = new BarControlPresenter();
        presenter.bind(this);

        sinBarRl = findViewById(R.id.sin_bar_rl);
        sinBarButton = findViewById(R.id.sin_bar_btn);
        barControlRl = findViewById(R.id.bar_control_rl);

        sinBarButton.setOnClickListener(v -> {
            //Crear bar
            //TODO: mock
            presenter.mockBarCreado();
            updateUI();
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (presenter.hayBarAsociado()) {
            sinBarRl.setVisibility(View.GONE);
            barControlRl.setVisibility(View.VISIBLE);
        } else {
            sinBarRl.setVisibility(View.VISIBLE);
            barControlRl.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        presenter.unbind();
        super.onDestroy();
    }
}
