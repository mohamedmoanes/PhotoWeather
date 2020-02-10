package com.mohamedmoanes.photoweather.utils

import android.graphics.Paint
import android.graphics.Rect

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