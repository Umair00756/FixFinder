package com.example.fixfinder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fixfinder.databinding.ActivityMainBinding;
import com.example.fixfinder.databinding.ActivityPlumberBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class plumber extends AppCompatActivity {

    String name,address,ph_no,date,time;
    final Calendar myCalender = Calendar.getInstance();
    EditText EditTextDate;
    EditText EditTextTime;
    Button button;

    ActivityPlumberBinding binding;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlumberBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

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

    binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name = binding.editTextName.getText().toString();
            address = binding.editTextAddress.getText().toString();
            ph_no = binding.editTextPhNo.getText().toString();
            date = binding.editTextDate.getText().toString();
            time = binding.editTextTime.getText().toString();

            if (!name.isEmpty() && !address.isEmpty() && !ph_no.isEmpty() && !date.isEmpty() && !time.isEmpty()){
                Users users = new Users(name,address,ph_no,date,time);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Users");
                reference.child(name).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.editTextName.setText("");
                        binding.editTextAddress.setText("");
                        binding.editTextPhNo.setText("");
                        binding.editTextDate.setText("");
                        binding.editTextTime.setText("");
                        Toast.makeText(plumber.this, "Successfully added.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    });

    binding.BtnBackVamos.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(plumber.this,userDashBoard.class);
            startActivity(intent);
        }
    });
    }

    private void init() {
        EditTextDate = findViewById(R.id.editTextDate);
        EditTextTime = findViewById(R.id.editTextTime);


    }
}
