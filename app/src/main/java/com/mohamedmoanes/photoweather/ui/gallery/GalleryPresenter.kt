package com.mohamedmoanes.photoweather.ui.gallery

import com.mohamedmoanes.photoweather.data.repositories.FilesRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class GalleryPresenter(private val galleryView: GalleryView) {
    private val filesRepo = FilesRepo()
    private lateinit var disposable: Disposable

    fun getGalleryItems(directory: File) {
        disposable = filesRepo.getGalleryFiles(directory).subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        )
            .subscribe({
                galleryView.setGalleryItems(it)
            }, {
                it.message?.let { it1 -> galleryView.onError(it1) }
            })
    }

    fun clear() {
        disposable.dispose()
    }
}