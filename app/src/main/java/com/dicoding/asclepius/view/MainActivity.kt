package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.view.ResultActivity.Companion.IMAGE_URI
import com.dicoding.asclepius.view.ResultActivity.Companion.RESULT_ML
import com.dicoding.asclepius.view.history.HistoryActivity
import crocodile8.image_picker_plus.ImagePickerPlus
import crocodile8.image_picker_plus.ImageTransformation
import crocodile8.image_picker_plus.PickRequest
import crocodile8.image_picker_plus.PickSource
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.analyzeButton.setOnClickListener {
            currentImageUri?.let {
                analyzeImage(it)
            } ?: run {
                showToast(getString(R.string.empty_image_warning))
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it?.data?.data?.let { uri ->
                currentImageUri = uri
                showImage()
            }
        }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        ImagePickerPlus
            .createIntent(
                activity = this,
                PickRequest(
                    source = PickSource.GALLERY,
                    transformation = ImageTransformation(
                        maxSidePx = 1024,
                    ),
                )
            )
            .let { launcher.launch(it) }
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        if (currentImageUri != null) {
            binding.previewImageView.setImageURI(currentImageUri)
        }
    }

    private fun analyzeImage(image: Uri) {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    runOnUiThread {
                        showToast(error)
                    }
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                println(it)
                                val sortedCategories =
                                    it[0].categories.sortedByDescending { it?.score }
                                val displayResult =
                                    sortedCategories.joinToString("\n") {
                                        "${it.label} " + NumberFormat.getPercentInstance()
                                            .format(it.score).trim()
                                    }
                                moveToResult(image, displayResult)
                            }
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(image)
    }

    private fun moveToResult(image: Uri, result: String) {
        val intent = Intent(this, ResultActivity::class.java)
        Log.d("CEK MAIN", "$image")
        intent.putExtra(IMAGE_URI, image.path.toString())
        intent.putExtra(RESULT_ML, result)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_history) {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}