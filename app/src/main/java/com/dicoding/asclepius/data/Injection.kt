package com.dicoding.asclepius.data

import android.content.Context

object Injection {
    fun provideRepository(context: Context): HistoryRepository {
        val database = AppDatabase.getInstance(context)
        val dao = database.dao()
        return HistoryRepository.getInstance(dao)
    }
}