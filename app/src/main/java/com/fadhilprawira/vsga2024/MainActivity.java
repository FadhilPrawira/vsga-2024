package com.fadhilprawira.vsga2024;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    private Sensor mLight;
    private TextView textView;
    private ImageView imageView;

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

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float lux = event.values[0];
        textView.setText(getString(R.string.nilai_sensor_cahaya_n, lux));

        if(lux <1000){
            imageView.setImageResource(R.drawable.face_smile_regular);
        } else {
            imageView.setImageResource(R.drawable.face_sad_cry_regular);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }
}