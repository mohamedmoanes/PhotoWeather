package com.mohamedmoanes.photoweather.data.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("visibility")
    val visibility: Int = 0,
    @SerializedName("timezone")
    val timezone: Int = 0,
    @SerializedName("main")
    val main: Main,
    @SerializedName("dt")
    val dt: Int = 0,

    @SerializedName("weather")
    val weather: List<WeatherItem>?,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("cod")
    val cod: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("base")
    val base: String = ""
)