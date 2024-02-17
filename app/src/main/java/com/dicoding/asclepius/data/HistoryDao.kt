package com.dicoding.asclepius.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertHistory(history: ModelHistory)

    @Query("SELECT * from table_history ORDER BY id ASC")
    suspend fun getAllHistory(): List<ModelHistory>
}