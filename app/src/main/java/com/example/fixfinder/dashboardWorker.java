package com.example.fixfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        if (user==null){
            Intent intent = new Intent(getApplicationContext() , userLogin.class);
            startActivity(intent);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext() , userSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}