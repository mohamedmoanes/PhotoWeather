package com.mohamedmoanes.photoweather.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

fun ImageView.loadFile(file: File) {
    this.post {
        Glide.with(this)
            .load(file)
            .into(this)
    }

}