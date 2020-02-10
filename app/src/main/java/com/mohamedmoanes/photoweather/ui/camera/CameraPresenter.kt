package com.mohamedmoanes.photoweather.ui.camera

import android.content.Context
import android.graphics.BitmapFactory
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.mohamedmoanes.photoweather.data.model.weather.WeatherModel
import com.mohamedmoanes.photoweather.data.repositories.FilesRepo
import com.mohamedmoanes.photoweather.data.repositories.WeatherRepo
import com.mohamedmoanes.photoweather.ui.base.BasePresenter
import com.mohamedmoanes.photoweather.ui.base.ResultListener
import com.mohamedmoanes.photoweather.utils.extensions.addLabel
import com.mohamedmoanes.photoweather.utils.getLocation
import com.mohamedmoanes.photoweather.utils.kelvinToCelsius
import com.mohamedmoanes.photoweather.utils.removeLocationListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class CameraPresenter(val cameraView: CameraView) : BasePresenter() {
    private val weatherRepo = WeatherRepo()
    private val filesRepo = FilesRepo()
    private lateinit var disposable: Disposable
    fun getThumbnailFiles(directory: File) {
        disposable =
            filesRepo.getThumbnailFile(directory).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    cameraView.setThumbnail(it)
                }, {
                    it.message?.let { it1 -> cameraView.onError(it1) }
                })
    }

    private fun getWeatherData(context: Context, file: File) {
        getLocation(
            context,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    val latitude: Double = locationResult.lastLocation.latitude
                    val longitude: Double = locationResult.lastLocation.longitude
                    getWeatherFromLocation(latitude, longitude, file)
                    removeLocationListener(context, this)

                }
            })
    }

    private fun getWeatherFromLocation(lat: Double, lon: Double, file: File) {
        singleSubscribe(
            weatherRepo.getGrandTotal(lat, lon, "b78beda793527aad4d20938ef614c55b"),
            object :
                ResultListener<WeatherModel> {
                override fun onSuccess(data: WeatherModel) {

                    val label = "${data.name}, ${data.weather!![0].description}, ${kelvinToCelsius(
                        data.main.temp
                    )}"
                    var bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    bitmap = bitmap.addLabel(label)
                    filesRepo.saveLabel(bitmap, file)
                }

                override fun onFailure(message: String) {
                    cameraView.onError(message)
                }
            })

    }

    fun addLabel(context: Context, file: File) {
        getWeatherData(context, file)
    }

    override fun clear() {
        super.clear()
        disposable.dispose()
    }
}