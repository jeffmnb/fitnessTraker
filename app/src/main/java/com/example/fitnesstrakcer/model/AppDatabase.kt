package com.example.fitnesstrakcer.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitnesstrakcer.utils.DateConverter

@Database(entities = [ImcCalc::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imcCalcDao(): ImcCalcDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return if (INSTANCE == null) {
                synchronized(context) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "fitness_tracker")
                            .build()
                }

                INSTANCE as AppDatabase
            } else {
                INSTANCE as AppDatabase
            }
        }
    }
}