package com.fadhilprawira.vsga2024;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ExternalActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String FILENAME = "namafile_external_storage.txt";
    Button btnBuatExternal, btnBacaExternal, btnHapusExternal;
    EditText textBacaExternal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_external);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnBuatExternal = findViewById(R.id.buttonBuatExternalFile);

        btnBacaExternal = findViewById(R.id.buttonBacaExternalFile);
        btnHapusExternal = findViewById(R.id.buttonHapusExternalFile);
        textBacaExternal = findViewById(R.id.textBacaExternal);

        btnBuatExternal.setOnClickListener(this);

        btnBacaExternal.setOnClickListener(this);
        btnHapusExternal.setOnClickListener(this);
        requestExternalStoragePermission();
    }
//Check permission based on Android SDK Version
void requestExternalStoragePermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
    }
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
        if(!Environment.isExternalStorageManager()){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityIfNeeded(intent,101);
            }catch (Exception e){
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            }
        }
    }
}
    public void buatExternalFile(){
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)){
            return;
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), FILENAME);

        FileOutputStream outputStream;
        try {
            if(file.createNewFile()){
                Toast.makeText(
                        ExternalActivity.this, "File dibuat", Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        ExternalActivity.this, "File sudah ada", Toast.LENGTH_SHORT
                ).show();
            }
            outputStream = new FileOutputStream(file, false);
            outputStream.write(textBacaExternal.getText().toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void bacaExternalFile(){
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(sdcard, FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line!=null){
                    text.append(line);
                    line = br.readLine();
                }

                br.close();

                Toast.makeText(
                        ExternalActivity.this, "File terbaca", Toast.LENGTH_SHORT
                ).show();
            } catch (IOException e){
                System.out.println("Error:"+e.getMessage());
            }

            textBacaExternal.setText(text.toString());
        }else {
            Toast.makeText(
                    ExternalActivity.this, "Tidak ada file", Toast.LENGTH_SHORT
            ).show();
        }

    }

    public void hapusExternalFile(){
        File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(sdcard, FILENAME);

        if (file.exists()){
            if(file.delete()){
                Toast.makeText(
                        ExternalActivity.this, "File dihapus", Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        ExternalActivity.this, "Tidak dapat menghapus file, cek kembali", Toast.LENGTH_SHORT
                ).show();
            }
            textBacaExternal.setText("");
        }else {
            Toast.makeText(
                    ExternalActivity.this, "Tidak ada file", Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    public void onClick(View v) {
        jalankanPerintah(v.getId());
    }

    public void jalankanPerintah(int id){
        if (id ==R.id.buttonBuatExternalFile){
            buatExternalFile();

        } else if (id ==R.id.buttonBacaExternalFile){
            bacaExternalFile();

        }else if (id==R.id.buttonHapusExternalFile){
            hapusExternalFile();
        }
    }
}