package com.example.fixfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class userLogin extends AppCompatActivity {
    EditText TextEmail, TextPassword;
    ProgressDialog progressDialog;
    TextView forgotPassword;
    Button loginButton;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), dashboardUser.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_login);
        TextEmail = findViewById(R.id.mailLoginWorker);
        TextPassword = findViewById(R.id.passwordLoginWorker);
        mAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.buttonSignupWorker);
        forgotPassword = findViewById(R.id.textView2);
        progressDialog = new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(TextEmail.getText());
                password = String.valueOf(TextPassword.getText());
                progressDialog.setTitle("Logging in...");
                progressDialog.show();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(userLogin.this, "Enter your email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(userLogin.this, "Enter your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.cancel();
                                if (task.isSuccessful()) {
                                    Toast.makeText(userLogin.this, "Login successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), dashboardUser.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressDialog.cancel();
                                    Toast.makeText(userLogin.this, "Invalid username or password.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = TextEmail.getText().toString();
                progressDialog.setTitle("Sending Mail.");
                progressDialog.show();
                mAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(userLogin.this, "Mail sent successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(userLogin.this, "Failed to send mail.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}