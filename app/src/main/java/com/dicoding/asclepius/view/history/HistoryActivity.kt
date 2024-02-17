package com.dicoding.asclepius.view.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.asclepius.databinding.ActivityHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapterHistory: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        historyViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(this)
        )[HistoryViewModel::class.java]

        adapterHistory = HistoryAdapter()
        recyclerview()
    }

    private fun recyclerview() {
        CoroutineScope(Dispatchers.Main).launch {
            val data = historyViewModel.getHistory()
            adapterHistory.submitList(data)
        }
        binding.rvHistory.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = adapterHistory
        }
    }
}