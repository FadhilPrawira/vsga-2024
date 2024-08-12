package com.fadhilprawira.vsga2024.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fadhilprawira.vsga2024.R;
import com.fadhilprawira.vsga2024.data.response.GempaItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class GempaAdapter extends RecyclerView.Adapter<GempaAdapter.ViewHolder> {

    private final ArrayList<GempaItem> listGempa;
    private OnItemClickCallback onItemClickCallback;

    public GempaAdapter(ArrayList<GempaItem> listGempa) {
        this.listGempa = listGempa;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(GempaItem data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_gempa, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        GempaItem gempa = listGempa.get(position);

        viewHolder.tvGempaMagnitude.setText(gempa.getMagnitude());
        viewHolder.tvGempaDatetime.setText(formatDateTime(gempa.getDateTime()));
        viewHolder.tvGempaWilayah.setText(gempa.getWilayah());
        viewHolder.tvGempaMmiDirasakan.setText(gempa.getDirasakan());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listGempa.get(viewHolder.getAdapterPosition()));
            }
        });

    }



    @Override
    public int getItemCount() {
        return listGempa.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<GempaItem> newList) {
        listGempa.clear();
        listGempa.addAll(newList);
        notifyDataSetChanged(); // Refresh the adapter
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvGempaMagnitude, tvGempaDatetime, tvGempaWilayah, tvGempaMmiDirasakan;

        public ViewHolder(View view) {
            super(view);
            tvGempaMagnitude = view.findViewById(R.id.tv_gempa_magnitude);
            tvGempaDatetime = view.findViewById(R.id.tv_gempa_datetime);
            tvGempaWilayah = view.findViewById(R.id.tv_gempa_wilayah);
            tvGempaMmiDirasakan = view.findViewById(R.id.tv_gempa_mmi_dirasakan);
        }
    }

    private String formatDateTime(String dateTime) {
        // Input format: 2024-08-12T11:36:13+00:00
        SimpleDateFormat inputFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        }
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        // Output format: 12 August 2024 18:36 WIB
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm z", Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta")); // WIB

        try {
            Date date = inputFormat.parse(dateTime);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime; // Return original if parsing fails
    }

}
