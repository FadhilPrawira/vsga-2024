package com.fadhilprawira.vsga2024.data.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Infogempa{

	@SerializedName("gempa")
	private List<GempaItem> gempa;

	public List<GempaItem> getGempa(){
		return gempa;
	}
}