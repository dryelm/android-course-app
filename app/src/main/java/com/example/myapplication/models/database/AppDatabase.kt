package com.example.myapplication.models.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.models.dao.HabitDao
import com.example.myapplication.models.entity.HabitEntity


@Database(entities = [HabitEntity::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}
