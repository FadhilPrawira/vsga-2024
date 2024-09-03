package com.fadhilprawira.vsga2024.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.fadhilprawira.vsga2024.R;
import com.fadhilprawira.vsga2024.data.response.GempaItem;
import com.fadhilprawira.vsga2024.databinding.ActivityGempaDetailBinding;
import com.fadhilprawira.vsga2024.utils.DateTimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
            binding.tvGempaMagnitudeDetail.setText(gempa.getMagnitude());
            binding.tvGempaWilayahDetail.setText(gempa.getWilayah());
            binding.tvGempaMmiDirasakanDetail.setText(gempa.getDirasakan());
            binding.tvGempaLintangDetail.setText(gempa.getLintang());
            binding.tvGempaBujurDetail.setText(gempa.getBujur());
            binding.tvGempaKedalamanDetail.setText(gempa.getKedalaman());
            binding.tvGempaDatetimeDetail.setText(DateTimeUtils.formatDateTime(gempa.getDateTime()));
        }

        if (gempa != null) {
            shakemapUrl = generateShakemapUrl(gempa.getDateTime());

            if (isNetworkAvailable()) {
                loadShakemapImage(shakemapUrl);
            } else {
                binding.tvImageStatus.setText(R.string.no_internet_connection_map_cannot_be_displayed);
                binding.tvImageStatus.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private void loadShakemapImage(String url) {
        // Show loading indicator and text
        binding.progressBarImage.setVisibility(View.VISIBLE);
        binding.tvImageStatus.setText(R.string.downloading_image);
        binding.tvImageStatus.setVisibility(View.VISIBLE);

        // Set a timeout of 15 second (15000 milliseconds)
        int timeout = 15000;

        // Create a custom Glide request
        RequestOptions options = new RequestOptions()
                .timeout(timeout);

        // Create a target to handle the image loading
        Target<Drawable> target = new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                binding.imgShakeMapDetail.setImageDrawable(resource);
                binding.progressBarImage.setVisibility(View.GONE);
                binding.tvImageStatus.setVisibility(View.GONE);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                binding.progressBarImage.setVisibility(View.GONE);
                binding.tvImageStatus.setText(R.string.download_image_failure);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                // Do nothing
            }
        };

        // Start the image loading
        Glide.with(this)
                .load(url)
                .apply(options)
                .into(target);

        // Set a timer to check if the download is taking too long
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (binding.progressBarImage.getVisibility() == View.VISIBLE) {
                    // If the progress bar is still visible after the timeout, consider it a failure
                    binding.progressBarImage.setVisibility(View.GONE);
                    binding.tvImageStatus.setText(R.string.download_image_failure);
                    Glide.with(GempaDetailActivity.this).clear(target);
                }
            }
        }, timeout + 100); // Add a small buffer to the timeout
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
