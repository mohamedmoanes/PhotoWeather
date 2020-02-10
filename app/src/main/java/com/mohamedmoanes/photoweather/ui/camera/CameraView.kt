package com.mohamedmoanes.photoweather.ui.camera

import java.io.File

interface CameraView {
    fun setWeatherText(s: String)
    fun setThumbnail(file: File)
    fun onError(msg: String)
}