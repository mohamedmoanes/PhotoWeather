package com.mohamedmoanes.photoweather.data.repositories

import com.mohamedmoanes.photoweather.data.model.weather.WeatherModel
import com.mohamedmoanes.photoweather.data.network.RetrofitClient
import io.reactivex.Single

class WeatherRepo {
    fun getGrandTotal(lat: Double, lon: Double, key: String): Single<WeatherModel> {
        return RetrofitClient.getService().getWeatherData(lat, lon, key)
    }
}