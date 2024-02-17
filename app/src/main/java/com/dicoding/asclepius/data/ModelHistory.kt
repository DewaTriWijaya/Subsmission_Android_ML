package com.dicoding.asclepius.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_history")
data class ModelHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val image: String,
    val result: String?,
    val scoring: String
)