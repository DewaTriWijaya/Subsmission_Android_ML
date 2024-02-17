package com.dicoding.asclepius.view.history

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.HistoryRepository
import com.dicoding.asclepius.data.ModelHistory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: HistoryRepository) : ViewModel() {
    suspend fun getHistory(): List<ModelHistory> {
        return repository.getAllHistory()
    }

    fun insertHistory(model: ModelHistory) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.insert(model)
        }
    }
}