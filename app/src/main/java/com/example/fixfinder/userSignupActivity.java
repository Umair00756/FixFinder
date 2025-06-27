package com.example.fixfinder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class userSignupActivity extends AppCompatActivity {
    EditText TextEmail,TextPassword;

    Button signUpButton;
    Button loginButton;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_signup);
        TextEmail = findViewById(R.id.mailLoginWorker);
        TextPassword = findViewById(R.id.passwordLoginWorker);
        mAuth = FirebaseAuth.getInstance();
        signUpButton = findViewById(R.id.buttonSignupWorker);
        loginButton = findViewById(R.id.buttonLoginSignup);
        progressDialog = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userSignupActivity.this,userLogin.class);
                startActivity(intent);
                finish();
            }

        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = String.valueOf(TextEmail.getText());
                progressDialog.setTitle("Signing in...");
                progressDialog.show();
                password = String.valueOf(TextPassword.getText());
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(userSignupActivity.this,"Enter your email." , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(userSignupActivity.this,"Enter your password." , Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.cancel();
                                if (task.isSuccessful()) {
                                    Toast.makeText(userSignupActivity.this, "SignUp Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),userLogin.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressDialog.cancel();
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(userSignupActivity.this, "Signup failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



    }
}