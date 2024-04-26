package com.example.assignment3;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.ArrayList;

public class RecycleBin extends AppCompatActivity {
    RecyclerView rvPasswords;
    BinAdapter myAdapter;
    ArrayList<Password> passwordList;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycle_bin);
        userid = getIntent().getIntExtra("userid", -1);
        init();
        rvPasswords = findViewById(R.id.rvPasswords);

        rvPasswords.setHasFixedSize(true);
        myAdapter = new BinAdapter(this, passwordList);
        rvPasswords.setLayoutManager(new LinearLayoutManager(this));
        rvPasswords.setAdapter(myAdapter);

    }

    private void init()
    {
        DatabaseHelper database = new DatabaseHelper(RecycleBin.this);
        database.open();
        passwordList = database.readDeleted(userid);
        database.close();
//        passwordList = new ArrayList<>();
//        passwordList.add(new Password(1, 1,"Hassan", "1234", "abcdef"));
//        passwordList.add(new Password(2, 1, "Jaffar", "1234", "abcdef"));

    }
}