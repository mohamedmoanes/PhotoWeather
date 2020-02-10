package com.mohamedmoanes.photoweather.app

import android.app.Application
import android.content.Context
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.facebook.stetho.Stetho
import com.mohamedmoanes.photoweather.BuildConfig

class PhotoWeatherApplication : Application(), CameraXConfig.Provider {
    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        //fixme remove this
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)

    }
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}
