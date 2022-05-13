package com.example.mynote.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.mynote.model.Note
import com.example.mynote.model.Text
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Note::class, Text::class], version = 1)
abstract class MyDatabase : RoomDatabase() {
    abstract fun dao(): TextDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MyDatabase::class.java,
                    "databse_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}