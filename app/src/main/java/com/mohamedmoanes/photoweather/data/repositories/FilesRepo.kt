package com.mohamedmoanes.photoweather.data.repositories

import android.graphics.Bitmap
import io.reactivex.Single
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FilesRepo {
    fun getThumbnailFile(directory: File): Single<File> {
        return Single.create<File> { emitter ->
            directory.listFiles()?.toMutableList()?.max()?.let {
                emitter.onSuccess(it)
            } ?: kotlin.run {
                emitter.onError(Throwable("No File Found"))
            }
        }
    }

    fun getGalleryFiles(directory: File): Single<MutableList<File>> {
        return Single.create<MutableList<File>> { emitter ->
            directory.listFiles()?.toMutableList()?.let {
                emitter.onSuccess(it)
            } ?: kotlin.run {
                emitter.onError(Throwable("No File Found"))
            }
        }
    }

    fun saveLabel(bitmap: Bitmap, file: File) {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos)
        val bitmapdata: ByteArray = bos.toByteArray()
        val fos: FileOutputStream
        try {
            fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: Throwable) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}