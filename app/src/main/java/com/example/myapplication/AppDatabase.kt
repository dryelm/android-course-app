package com.example.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.room_database.HabitDao
import com.example.data.room_database.HabitEntity


@Database(entities = [HabitEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}
