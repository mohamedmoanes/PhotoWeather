package com.mohamedmoanes.photoweather.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun getRootDirectory(context: Context):File{
    return context.externalMediaDirs.first()
}

fun createFile(
    baseFolder: File,
    format: String = "yyyy-MM-dd-HH:mm:ss",
    extension: String = ".JPEG"
) =
    File(
        baseFolder, SimpleDateFormat(format, Locale.US)
            .format(System.currentTimeMillis()) + extension
    )