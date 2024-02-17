package com.dicoding.asclepius.data

class HistoryRepository private constructor(
    private val dao: HistoryDao
) {
    suspend fun insert(model: ModelHistory) {
        return dao.insertHistory(model)
    }

    suspend fun getAllHistory(): List<ModelHistory> {
        return dao.getAllHistory()
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            historyDao: HistoryDao
        ): HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao)
            }.also { instance = it }

    }
}