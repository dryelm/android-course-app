package com.example.myapplication.models

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myapplication.Habit
import com.example.myapplication.models.dao.HabitDao
import com.example.myapplication.models.database.AppDatabase
import com.example.myapplication.models.entity.HabitEntity

class HabitsModel private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: HabitsModel? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: HabitsModel(context).also { instance = it }
            }
    }

    private val habitDao: HabitDao

    init {
        val db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "habit"
        ).allowMainThreadQueries().build()
        habitDao = db.habitDao()
    }

    fun add(habit: Habit){
        habitDao.insert(HabitEntity.toEntity(habit))
    }

    fun getAll(): LiveData<List<HabitEntity>> {
        Log.d(this.toString(), "getAll")
        return habitDao.getAll()
    }

    fun getById(id: Int): LiveData<HabitEntity> {
        return habitDao.getById(id)
    }




    fun update(habit: Habit){
        habitDao.update(HabitEntity.toEntity(habit))
    }

}