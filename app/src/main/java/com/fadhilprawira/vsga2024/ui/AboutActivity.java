package com.fadhilprawira.vsga2024.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fadhilprawira.vsga2024.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.about_me); // Set title from strings.xml
        }
    }
}