package com.fadhilprawira.vsga2024.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fadhilprawira.vsga2024.R;
import com.fadhilprawira.vsga2024.data.response.GempaItem;
import com.fadhilprawira.vsga2024.databinding.ActivityGempaDetailBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GempaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "extra_item";

    private ActivityGempaDetailBinding binding;

    String shakemapUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGempaDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.detail_gempa); // Set title from strings.xml
        }

        GempaItem gempa;
        if (android.os.Build.VERSION.SDK_INT >= 33) {
            gempa = getIntent().getParcelableExtra(EXTRA_ITEM, GempaItem.class);
        } else {
            gempa = getIntent().getParcelableExtra(EXTRA_ITEM);
        }
        if (gempa != null) {
            binding.tvGempaMagnitude.setText(gempa.getMagnitude());
            binding.tvGempaWilayah.setText(gempa.getWilayah());
            binding.tvGempaMmiDirasakan.setText(gempa.getDirasakan());
        }

        // Load the shakemap image with Glide
        if (gempa != null) {
            shakemapUrl = generateShakemapUrl(gempa.getDateTime());
            Glide.with(this)
                    .load(shakemapUrl)
                    .into(binding.imgShakeMap);
        }
    }

    // Helper method to generate Shakemap URL
    private String generateShakemapUrl(String dateTime) {
        // Input format: 2024-08-12T11:36:13+00:00
        SimpleDateFormat inputFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        }
        if (inputFormat != null) {
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Input is in UTC
        }

        // Output format: 20240812183613 (yyyyMMddHHmmss)
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta")); // Output should be in WIB

        try {
            // Parse the input datetime
            Date date = null;
            if (inputFormat != null) {
                date = inputFormat.parse(dateTime);
            }
            if (date != null) {
                // Format the date to the required URL format
                String formattedDate = outputFormat.format(date);
                return "https://data.bmkg.go.id/DataMKG/TEWS/" + formattedDate + ".mmi.jpg";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null; // Return null if parsing or formatting fails
    }

}