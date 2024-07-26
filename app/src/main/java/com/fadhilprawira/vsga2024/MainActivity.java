package com.fadhilprawira.vsga2024;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnInternal, btnExternal;
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

        btnInternal = findViewById(R.id.buttonInternalActivity);
        btnExternal = findViewById(R.id.buttonExternalActivity);

        btnInternal.setOnClickListener(this);
        btnExternal.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonInternalActivity){
//            Dicoding style
            Intent moveIntent = new Intent(MainActivity.this, InternalActivity.class);
            startActivity(moveIntent);
        }
        else if (v.getId() == R.id.buttonExternalActivity){
            Intent moveIntent = new Intent(MainActivity.this, ExternalActivity.class);
            startActivity(moveIntent);
        }
    }
}