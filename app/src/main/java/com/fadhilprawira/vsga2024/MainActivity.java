package com.fadhilprawira.vsga2024;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fadhilprawira.vsga2024.data.response.DaftarGempaResponse;
import com.fadhilprawira.vsga2024.data.response.GempaItem;
import com.fadhilprawira.vsga2024.data.retrofit.ApiConfig;
import com.fadhilprawira.vsga2024.databinding.ActivityMainBinding;
import com.fadhilprawira.vsga2024.ui.AboutActivity;
import com.fadhilprawira.vsga2024.ui.GempaAdapter;
import com.fadhilprawira.vsga2024.ui.GempaDetailActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private GempaAdapter gempaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name); // Set title from strings.xml
        }

        FloatingActionButton fab = binding.fabRefresh;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading(true); // Ensure this line is called
                fetchGempaData();  // Fetch data when FAB is clicked
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvGempa.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.rvGempa.addItemDecoration(itemDecoration);

        // Initialize adapter with an empty list
        gempaAdapter = new GempaAdapter(new ArrayList<>());
        gempaAdapter.setOnItemClickCallback(new GempaAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(GempaItem data) {
                showSelectedGempa(data); // Show Toast when an item is clicked
                // Optionally start DetailActivity here if needed
                Intent intent = new Intent(MainActivity.this, GempaDetailActivity.class);
                intent.putExtra(GempaDetailActivity.EXTRA_ITEM, data);
                startActivity(intent);
            }
        });

        binding.rvGempa.setAdapter(gempaAdapter);

        // Set up the SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh the data
                fetchGempaData();
            }
        });

        // Fetch data from API
        fetchGempaData();
    }


    private void fetchGempaData() {
        showLoading(true);
        binding.textViewNoInternet.setVisibility(View.GONE);
        binding.rvGempa.setVisibility(View.VISIBLE);

        ApiConfig.getApiService().getInfogempa().enqueue(new Callback<DaftarGempaResponse>() {
            @Override
            public void onResponse(@NonNull Call<DaftarGempaResponse> call, @NonNull Response<DaftarGempaResponse> response) {
                showLoading(false);  // Hide ProgressBar when data is loaded
                binding.swipeRefreshLayout.setRefreshing(false); // Stop refresh animation on failure
                if (response.isSuccessful() && response.body() != null) {
                    List<GempaItem> gempaList = response.body().getInfogempa().getGempa();
                    gempaAdapter.updateData(gempaList); // Update adapter with new data
                    Log.d(TAG, "Data successfully fetched and adapter updated");
                } else {
                    Log.e(TAG, "onResponse: Failed to get data");
                    Toast.makeText(MainActivity.this, "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DaftarGempaResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "fetchGempaData onFailure called");
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                showLoading(false); // Hide the ProgressBar if it was shown
                binding.swipeRefreshLayout.setRefreshing(false); // Stop refresh animation on failure
                if (gempaAdapter.getItemCount() > 0) {
                    // RecyclerView has data, show it and the toast
                    Toast.makeText(MainActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    binding.rvGempa.setVisibility(View.VISIBLE); // Ensure the RecyclerView stays visible
                    binding.textViewNoInternet.setVisibility(View.GONE); // Hide the textViewNoInternet
                } else {
                    // RecyclerView is empty, show the toast and textViewNoInternet
                    Toast.makeText(MainActivity.this, "No internet connection, data cannot be loaded", Toast.LENGTH_SHORT).show();
                    binding.textViewNoInternet.setVisibility(View.VISIBLE); // Show the textViewNoInternet
                    binding.rvGempa.setVisibility(View.GONE); // Hide the RecyclerView
                }
            }

        });
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            Log.d(TAG, "ProgressBar set to VISIBLE");
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            Log.d(TAG, "ProgressBar set to GONE");
            binding.progressBar.setVisibility(View.GONE);
            binding.swipeRefreshLayout.setRefreshing(false); // Ensure SwipeRefreshLayout stops refreshing
        }
    }


    private void showSelectedGempa(GempaItem gempa) {
        Toast.makeText(this, "Kamu memilih " + gempa.getWilayah(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.about) {
            Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intentAbout);
        }
        return super.onOptionsItemSelected(item);
    }

}
