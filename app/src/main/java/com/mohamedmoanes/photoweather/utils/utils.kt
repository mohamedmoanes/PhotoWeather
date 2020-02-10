package com.mohamedmoanes.photoweather.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.util.*

fun getRootDirectory(context: Context):File{
    return context.externalMediaDirs.first()
}