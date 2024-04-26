package com.example.assignment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity {
    RecyclerView rvPasswords;

    FloatingActionButton btnBin, btnAdd;
    PasswordAdapter myAdapter;
    ArrayList<Password> passwordList;

    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        userid = getIntent().getIntExtra("userid", -1);

        init();

        rvPasswords.setHasFixedSize(true);
        myAdapter = new PasswordAdapter(this, passwordList);
        rvPasswords.setLayoutManager(new LinearLayoutManager(this));
        rvPasswords.setAdapter(myAdapter);

        btnBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, RecycleBin.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, AddPassword.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
    }
    private void init()
    {
        btnBin = findViewById(R.id.btnBin);
        btnAdd = findViewById(R.id.btnAdd);
        rvPasswords = findViewById(R.id.rvPasswords);

        DatabaseHelper database = new DatabaseHelper(this);
        database.open();
        passwordList = database.readAllPasswords(userid);
        database.close();

//        passwordList = new ArrayList<>();
//        passwordList.add(new Password(1, 1,"Hassan", "1234", "abcdef"));
//        passwordList.add(new Password(2, 1, "Jaffar", "1234", "abcdef"));
//        passwordList.add(new Password(3, 2, "Hassan", "1234", "abcdef"));
//        passwordList.add(new Password(4, 1, "Jaffar", "1234", "abcdef"));
//        passwordList.add(new Password(5, 1,"Hassan", "1234", "abcdef"));
//        passwordList.add(new Password(6, 2, "Jaffar", "1234", "abcdef"));
//        passwordList.add(new Password(7, 1, "Hassan", "1234", "abcdef"));
//        passwordList.add(new Password(8, 2, "Jaffar", "1234", "abcdef"));
    }
}