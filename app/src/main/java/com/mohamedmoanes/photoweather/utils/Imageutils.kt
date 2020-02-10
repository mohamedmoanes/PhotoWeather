package com.mohamedmoanes.photoweather.utils

import android.graphics.*
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

fun loadImageFromFile(file: File, imageView: ImageView) {
    imageView.post {
        Glide.with(imageView)
            .load(file)
            .into(imageView)
    }
}
fun getTextBackgroundSize(x: Float, y: Float, text: String, paint: Paint): Rect {
    val fontMetrics = paint.fontMetrics
    val textLength = paint.measureText(text)
    return Rect(
        (x - textLength).toInt(),
        (y + fontMetrics.top).toInt(),
        (x + textLength).toInt(),
        (y + fontMetrics.bottom).toInt()
    )
}