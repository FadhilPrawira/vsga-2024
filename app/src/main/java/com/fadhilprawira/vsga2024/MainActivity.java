package com.fadhilprawira.vsga2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private DatabaseHelper databaseHelper;
    private TextView tvnames;
    private Button btnStore, btnget;
    private EditText etname;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        databaseHelper = new DatabaseHelper(this);
        tvnames = findViewById(R.id.tvnames);
        btnStore = findViewById(R.id.btnStore);
        btnget = findViewById(R.id.btnget);
        etname = findViewById(R.id.etname);

        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                If name is empty, show toast
                if (etname.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                databaseHelper.addStudentDetail(etname.getText().toString());
                etname.setText("");
                Toast.makeText(MainActivity.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = databaseHelper.getAllStudentsList();
//                If no data, show toast
                if (arrayList.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No data found in Database!", Toast.LENGTH_SHORT).show();
                    return;
                }
//                Display all names as 1. name, 2. name, 3. name, ...
                StringBuilder namesBuilder = new StringBuilder();
                for (int i = 0; i < arrayList.size(); i++) {
                    namesBuilder.append(i + 1).append(". ").append(arrayList.get(i)).append("\n");
                }
                tvnames.setText(namesBuilder.toString());
                Toast.makeText(MainActivity.this, "Retrieved Successfully!", Toast.LENGTH_SHORT).show();


            }
        });
    }

}