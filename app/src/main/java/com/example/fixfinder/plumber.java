package com.example.fixfinder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class plumber extends AppCompatActivity {
    final Calendar myCalender = Calendar.getInstance();
    EditText EditTextDate;
    EditText EditTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);

        init();

        EditTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(plumber.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalender.set(Calendar.YEAR, year);
                        myCalender.set(Calendar.MONTH, month);
                        myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String myFormat = "dd-MM-yyyy";
                        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                        EditTextDate.setText(dateFormat.format(myCalender.getTime()));
                    }
                }, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = myCalender.get(Calendar.HOUR_OF_DAY);
                int minute = myCalender.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(plumber.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                myCalender.set(Calendar.MINUTE, minute);

                                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);
                                EditTextTime.setText(timeFormat.format(myCalender.getTime()));
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    private void init() {
        EditTextDate = findViewById(R.id.editTextDate);
        EditTextTime = findViewById(R.id.editTextTime);
    }
}
