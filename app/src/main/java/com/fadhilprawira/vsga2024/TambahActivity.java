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

import java.io.File;
import java.io.FileOutputStream;

public class TambahActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSimpan;
    EditText edtTextNamaFile, edtTextIsiCatatan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tambah);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnSimpan = findViewById(R.id.simpanButton);
        edtTextNamaFile = findViewById(R.id.namaFileEditText);
        edtTextIsiCatatan = findViewById(R.id.catatanEditText);

        btnSimpan.setOnClickListener(this);
    }


    public void simpan() {
//        Sanity check
        String namaFile = edtTextNamaFile.getText().toString();
        if(namaFile.isEmpty()) {
            Toast.makeText(
                    TambahActivity.this, R.string.nama_file_tidak_boleh_kosong, Toast.LENGTH_SHORT
            ).show();
            return;

        }
        String isiCatatan = edtTextIsiCatatan.getText().toString();
        if (isiCatatan.isEmpty()) {
            Toast.makeText(
                    TambahActivity.this, R.string.isi_catatan_tidak_boleh_kosong, Toast.LENGTH_SHORT
            ).show();
            return;
        }
        Toast.makeText(
                TambahActivity.this, "Nama file:"+namaFile+"\nIsi catatan:"+isiCatatan, Toast.LENGTH_SHORT
        ).show();
        buatFile(namaFile, isiCatatan);
        finish();
    }

    @Override
    public void onClick(View v) {
        jalankanPerintah(v.getId());
    }

    public void jalankanPerintah(int id) {
        if (id == R.id.simpanButton) {
            simpan();
        }
    }

    void buatFile(String namaFile, String isiCatatan){

        File file = new File(getFilesDir(), namaFile);

        FileOutputStream outputStream;
        try {
            if(file.createNewFile()){
                Toast.makeText(
                        TambahActivity.this, "File dibuat", Toast.LENGTH_SHORT
                ).show();
            }else {
                Toast.makeText(
                        TambahActivity.this, "File sudah ada", Toast.LENGTH_SHORT
                ).show();
            }
            outputStream = new FileOutputStream(file, false);
            outputStream.write(isiCatatan.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}