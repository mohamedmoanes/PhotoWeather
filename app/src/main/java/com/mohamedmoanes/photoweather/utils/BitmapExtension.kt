package com.mohamedmoanes.photoweather.utils

import android.graphics.*
import com.mohamedmoanes.photoweather.R
import com.mohamedmoanes.photoweather.app.PhotoWeatherApplication

fun Bitmap.addLabel(weatherString: String): Bitmap {

    val bitmap = this.copy(Bitmap.Config.RGB_565, true)

    val x = 20f
    val y = bitmap.height.toFloat() - 20

    //Draw overlay:
    val canvas = Canvas(bitmap)

    val textPaint = Paint(Paint.FILTER_BITMAP_FLAG)
    textPaint.color = Color.BLACK
    textPaint.textSize =
        PhotoWeatherApplication.appContext.resources.getDimension(R.dimen.text_size)

    val bgPaint = Paint(Paint.FILTER_BITMAP_FLAG)
    bgPaint.color = Color.RED

    val background = getTextBackgroundSize(x, y, weatherString, textPaint)
    canvas.drawRect(background, bgPaint)

    canvas.drawText(weatherString, x, y, textPaint)
    return bitmap
}