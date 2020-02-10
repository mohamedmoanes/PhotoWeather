package com.mohamedmoanes.photoweather.ui.camera

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.mohamedmoanes.photoweather.R
import com.mohamedmoanes.photoweather.ui.gallery.GalleryActivity
import com.mohamedmoanes.photoweather.utils.createFile
import com.mohamedmoanes.photoweather.utils.extensions.loadFile
import com.mohamedmoanes.photoweather.utils.getRootDirectory
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.util.concurrent.Executors

class CameraViewActivity : AppCompatActivity(), CameraView {
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture: ImageCapture
    private var weatherString: String? = null
    private val presenter =
        CameraPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        initCameraPreview()

        iniCameraCapture()

        thumbnailHandler()

        presenter.getThumbnailFiles(getRootDirectory(this))
    }

    private fun initCameraPreview() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun iniCameraCapture() {
        imgCapture.setOnClickListener {
            capture()
        }
    }

    private fun thumbnailHandler() {
        thumbnail.setOnClickListener {
            val i = Intent(this, GalleryActivity::class.java)
            startActivity(i)
        }
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview: Preview = Preview.Builder()
            .setTargetName("Preview")
            .build()

        preview.previewSurfaceProvider = preview_view.previewSurfaceProvider

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()


        imageCapture = ImageCapture.Builder()
            .setTargetRotation(windowManager.defaultDisplay.rotation)
            .build()

        cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
    }

    private fun capture() {
        val file = createFile(getRootDirectory(this))
        val executor = Executors.newSingleThreadExecutor()
        imageCapture.takePicture(file,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(file: File) {
                    thumbnail.loadFile(file)
                    runOnUiThread {
                        presenter.addLabel(this@CameraViewActivity, file)
                    }

                }

                override fun onError(imageCaptureError: Int, message: String, cause: Throwable?) {
                    runOnUiThread {
                        Toast.makeText(
                            this@CameraViewActivity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }


    override fun setWeatherText(s: String) {
        weatherString = s
        imgCapture.isClickable = true
    }

    override fun setThumbnail(file: File) {
        thumbnail.loadFile(file)
    }

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        presenter.clear()
        super.onDestroy()
    }
}
