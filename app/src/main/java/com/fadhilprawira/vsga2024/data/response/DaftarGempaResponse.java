package com.fadhilprawira.vsga2024.data.response;

import com.google.gson.annotations.SerializedName;

public class DaftarGempaResponse{

	@SerializedName("Infogempa")
	private Infogempa infogempa;

	public Infogempa getInfogempa(){
		return infogempa;
	}
}