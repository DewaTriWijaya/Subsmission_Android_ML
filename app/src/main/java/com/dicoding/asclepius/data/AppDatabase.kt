package com.dicoding.asclepius.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ModelHistory::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun dao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            val instanceA = INSTANCE
            if (instanceA != null) {
                return instanceA
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "history_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}