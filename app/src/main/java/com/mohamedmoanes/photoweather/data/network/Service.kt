package com.mohamedmoanes.photoweather.data.network

import com.mohamedmoanes.photoweather.data.model.weather.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("weather")
    fun getWeatherData(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("appid") appid: String): Single<WeatherModel>
}