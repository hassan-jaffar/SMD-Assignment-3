package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {

    Button btnSignup;
    TextInputEditText etUsername, etPassword, etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        init();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etUsername.getText().toString().trim();
                String pass = etPassword.getText().toString().trim();
                String pass2 = etConfirmPassword.getText().toString().trim();

                if(pass.equals(pass2))
                {
                    DatabaseHelper database = new DatabaseHelper(Signup.this);
                    database.open();
                    database.signupUser(name, pass);
                    database.close();
                }
                else{
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        btnSignup = findViewById(R.id.btnSignup);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
    }
}