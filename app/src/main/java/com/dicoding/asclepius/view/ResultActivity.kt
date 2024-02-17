package com.dicoding.asclepius.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.asclepius.data.ModelHistory
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.view.history.HistoryViewModel
import com.dicoding.asclepius.view.history.ViewModelFactory


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[HistoryViewModel::class.java]

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        resultML()
    }

    @SuppressLint("SetTextI18n")
    private fun resultML() {
        val image = intent.getStringExtra(IMAGE_URI)
        val resultUrl = intent.getStringExtra(RESULT_ML)
        Log.d("CEK ", "$resultUrl")

        if (resultUrl != null) {
            val parts = resultUrl.split("%")
            val result1 = parts[0].trim()
            val label = result1.replace(Regex("\\d+"), "")

            val regex = Regex("\\d+")
            val scoring = regex.find(result1)

            if (image != null) {
                Glide.with(this)
                    .load(image)
                    .into(binding.resultImage)

                binding.resultText.text = "$result1%"

                binding.btnSave.setOnClickListener {
                    historyViewModel.insertHistory(
                        ModelHistory(
                            image = image,
                            result = label,
                            scoring = "${scoring?.value?.toInt()}"
                        )
                    )
                    startActivity(Intent(this, MainActivity::class.java))
                }

                binding.btnHome.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        }
    }

    companion object {
        const val IMAGE_URI = "image_uri"
        const val RESULT_ML = "result_ml"
    }
}