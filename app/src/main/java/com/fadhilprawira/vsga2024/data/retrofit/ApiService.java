package com.fadhilprawira.vsga2024.data.retrofit;

import com.fadhilprawira.vsga2024.data.response.DaftarGempaResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("DataMKG/TEWS/gempadirasakan.json")
    Call<DaftarGempaResponse> getInfogempa();

}
