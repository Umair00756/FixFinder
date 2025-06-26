package com.example.fixfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboardWorker extends AppCompatActivity {

    FirebaseAuth auth;
    Button btn;
    TextView text;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dashboard_worker);

        auth = FirebaseAuth.getInstance();
        btn = findViewById(R.id.button5);
        user = auth.getCurrentUser();


        Spinner serviceDropDown = findViewById(R.id.serviceDropDown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.services_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceDropDown.setAdapter(adapter);

        serviceDropDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                serviceDropDown.performClick();
                return false;
            }
        });


        serviceDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                Toast.makeText(dashboardWorker.this, "Selected: " + selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), userLogin.class);
            startActivity(intent);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), userSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
