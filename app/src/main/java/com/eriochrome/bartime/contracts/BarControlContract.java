package com.eriochrome.bartime.contracts;

public interface BarControlContract {

    interface Interaccion {
        void hayBarAsociado();
    }

    interface View {

    }

    interface CompleteListener {
        void onComplete(boolean hayBar);
    }
}