package com.mohamedmoanes.photoweather.ui

import android.content.Intent
import android.media.MediaScannerConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.MimeTypeMap
import androidx.appcompat.app.AlertDialog
import com.mohamedmoanes.photoweather.R
import com.mohamedmoanes.photoweather.utils.loadImageFromFile
import kotlinx.android.synthetic.main.activity_image_viewer.*
import java.io.File

class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)
        val path = intent.getStringExtra("path")
        if (null != path) {
            val imageFile = File(path)

            loadImageFromFile(imageFile, image_view)


            initDelete(imageFile)

            initShare(imageFile)
        }
    }

    private fun initDelete(imageFile: File) {
        delete.setOnClickListener {
            AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog)
                .setTitle(getString(R.string.delete_title))
                .setMessage(getString(R.string.delete_dialog))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    // Delete current photo
                    imageFile.delete()

                    // Send relevant broadcast to notify other apps of deletion
                    MediaScannerConnection.scanFile(
                        this, arrayOf(imageFile.absolutePath), null, null
                    )

                    onBackPressed()
                }

                .setNegativeButton(android.R.string.no, null)
                .create().show()
        }
    }

    private fun initShare(imageFile: File) {
        share.setOnClickListener {
            // Create a sharing intent
            val intent = Intent().apply {
                // Infer media type from file extension
                val mediaType = MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(imageFile.extension)

                // Get URI from our file
                val uri = imageFile.toURI()

                // Set the appropriate intent extra, type, action and flags
                putExtra(Intent.EXTRA_STREAM, uri)
                type = mediaType
                action = Intent.ACTION_SEND
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            // Launch the intent letting the user choose which app to share with
            startActivity(Intent.createChooser(intent, getString(R.string.share_hint)))
        }
    }
}
