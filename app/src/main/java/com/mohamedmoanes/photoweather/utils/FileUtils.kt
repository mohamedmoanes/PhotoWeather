package com.mohamedmoanes.photoweather.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

fun getRootDirectory(context: Context): File {
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