package com.example.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class AddPassword extends AppCompatActivity {

    Button btnAdd;
    TextInputEditText etUsername, etPassword, etUrl;
    int userid;
    PasswordAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_password);
        userid = getIntent().getIntExtra("userid", -1);
        init();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = etUrl.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                DatabaseHelper database = new DatabaseHelper(AddPassword.this);
                database.open();
                database.addPassword(userid, url, username, password);
                database.close();
                myAdapter.notifyDataSetChanged();
                Intent intent = new Intent(AddPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void init(){
        btnAdd = findViewById(R.id.btnAdd);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etUrl = findViewById(R.id.etUrl);
    }
}