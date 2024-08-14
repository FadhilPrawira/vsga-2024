package com.fadhilprawira.vsga2024.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    public static String formatDateTime(String dateTime) {
        // Input format: 2024-08-12T11:36:13+00:00
        SimpleDateFormat inputFormat = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        }
        if (inputFormat != null) {
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        // Output format: 12 August 2024 18:36 WIB
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm z", Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta")); // WIB

        try {
            Date date = null;
            if (inputFormat != null) {
                date = inputFormat.parse(dateTime);
            }
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime; // Return original if parsing fails
    }
}