package com.eriochrome.bartime.utils;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class TimePicker implements TimePickerDialog.OnTimeSetListener, View.OnFocusChangeListener, View.OnClickListener {

    private Context context;
    private EditText horarioEditText;

    public TimePicker(EditText horarioEditText, Context context) {
        this.context = context;
        this.horarioEditText = horarioEditText;
        this.horarioEditText.setOnFocusChangeListener(this);
        this.horarioEditText.setOnClickListener(this);
        this.horarioEditText.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        this.horarioEditText.setText(String.valueOf(hourOfDay));
        this.horarioEditText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        if (hasFocus) {
            new TimePickerDialog(context, this, 0, 0, true).show();
        }
    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        new TimePickerDialog(context, this, 0, 0, true).show();
    }
}
