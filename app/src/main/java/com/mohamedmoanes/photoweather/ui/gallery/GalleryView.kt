package com.mohamedmoanes.photoweather.ui.gallery

import java.io.File

interface GalleryView {
    fun setGalleryItems(items: MutableList<File>)
    fun onError(msg:String)
}