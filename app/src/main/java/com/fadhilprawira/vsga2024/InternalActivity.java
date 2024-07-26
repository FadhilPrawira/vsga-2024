package com.fadhilprawira.vsga2024;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class InternalActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String FILENAME = "namafile_internal_storage.txt";
    Button buatFile, bacaFile, deleteFile;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_internal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buatFile = findViewById(R.id.buttonBuatFile);
        bacaFile = findViewById(R.id.buttonBacaFile);
        deleteFile = findViewById(R.id.buttonHapusFile);
        editText = findViewById(R.id.textBaca);

        bacaFile();

        buatFile.setOnClickListener(this);
        bacaFile.setOnClickListener(this);
        deleteFile.setOnClickListener(this);
    }

    void buatFile(){

        File file = new File(getFilesDir(), FILENAME);

        FileOutputStream outputStream;
        try {
            if(file.createNewFile()){
                Toast.makeText(
                        InternalActivity.this, "File dibuat", Toast.LENGTH_SHORT
                ).show();
            }else {
                Toast.makeText(
                        InternalActivity.this, "File sudah ada", Toast.LENGTH_SHORT
                ).show();
            }
            outputStream = new FileOutputStream(file, false);
            outputStream.write(editText.getText().toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void bacaFile(){
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

        if(file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();

                while(line!=null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
                Toast.makeText(
                        InternalActivity.this, "File terbaca", Toast.LENGTH_SHORT
                ).show();
            } catch (IOException e){
                System.out.println("Error:"+e.getMessage());
            }

            editText.setText(text.toString());
        } else {
            Toast.makeText(
                    InternalActivity.this, "Tidak ada file", Toast.LENGTH_SHORT
            ).show();
        }
    }

    void hapusFile() {
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()) {
            if(file.delete()){
                Toast.makeText(
                        InternalActivity.this, "File dihapus", Toast.LENGTH_SHORT
                ).show();
            } else {
                Toast.makeText(
                        InternalActivity.this, "Tidak dapat menghapus file, cek kembali", Toast.LENGTH_SHORT
                ).show();
            }
            editText.setText("");
        }
        else {
            Toast.makeText(
                    InternalActivity.this, "Tidak ada file", Toast.LENGTH_SHORT
            ).show();
        }
    }

    @Override
    public void onClick(View v) {
        jalankanPerintah(v.getId());

    }

    public void jalankanPerintah(int id){
        if (id ==R.id.buttonBuatFile){
            buatFile();
        } else if (id ==R.id.buttonBacaFile){
            bacaFile();
        } else if (id==R.id.buttonHapusFile){
            hapusFile();
        }
//        In recent versions of Android development, especially when using Android Gradle Plugin 7.0 or higher, resource IDs (R.id.*) are no longer final due to a new feature called non-final resource IDs.
    }
}