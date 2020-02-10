package com.mohamedmoanes.photoweather.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.mohamedmoanes.photoweather.R
import com.mohamedmoanes.photoweather.ui.ImageViewerActivity
import com.mohamedmoanes.photoweather.ui.gallery.adapters.GalleryAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.File

class GalleryActivity : AppCompatActivity(), GalleryView {
    private val presenter = GalleryPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val rootDirectory = externalMediaDirs.first()
        presenter.getGalleryItems(rootDirectory)
    }

    private fun initGalleryList(list: List<File>) {
        photo_list.layoutManager = GridLayoutManager(this, 3)
        photo_list.adapter = GalleryAdapter(list, ::openPhoto)
    }

    private fun openPhoto(path: String) {
        val i = Intent(this, ImageViewerActivity::class.java)
        i.putExtra("path", path)
        startActivity(i)
    }

    override fun setGalleryItems(items: MutableList<File>) {
        initGalleryList(items)
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.clear()
        super.onDestroy()
    }
}
