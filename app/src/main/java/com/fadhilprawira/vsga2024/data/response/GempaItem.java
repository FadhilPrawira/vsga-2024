package com.fadhilprawira.vsga2024.data.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class GempaItem implements Parcelable {

    @SerializedName("Dirasakan")
    private String dirasakan;

    @SerializedName("Wilayah")
    private String wilayah;

    @SerializedName("Kedalaman")
    private String kedalaman;

    @SerializedName("Jam")
    private String jam;

    @SerializedName("Coordinates")
    private String coordinates;

    @SerializedName("Tanggal")
    private String tanggal;

    @SerializedName("Bujur")
    private String bujur;

    @SerializedName("Magnitude")
    private String magnitude;

    @SerializedName("Lintang")
    private String lintang;

    @SerializedName("DateTime")
    private String dateTime;

    protected GempaItem(Parcel in) {
        dirasakan = in.readString();
        wilayah = in.readString();
        kedalaman = in.readString();
        jam = in.readString();
        coordinates = in.readString();
        tanggal = in.readString();
        bujur = in.readString();
        magnitude = in.readString();
        lintang = in.readString();
        dateTime = in.readString();
    }

    public static final Creator<GempaItem> CREATOR = new Creator<GempaItem>() {
        @Override
        public GempaItem createFromParcel(Parcel in) {
            return new GempaItem(in);
        }

        @Override
        public GempaItem[] newArray(int size) {
            return new GempaItem[size];
        }
    };

    public String getDirasakan() {
        return dirasakan;
    }

    public String getWilayah() {
        return wilayah;
    }

    public String getKedalaman() {
        return kedalaman;
    }

    public String getJam() {
        return jam;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getBujur() {
        return bujur;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public String getLintang() {
        return lintang;
    }

    public String getDateTime() {
        return dateTime;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(dirasakan);
        dest.writeString(wilayah);
        dest.writeString(kedalaman);
        dest.writeString(jam);
        dest.writeString(coordinates);
        dest.writeString(tanggal);
        dest.writeString(bujur);
        dest.writeString(magnitude);
        dest.writeString(lintang);
        dest.writeString(dateTime);
    }
}

