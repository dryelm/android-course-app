package com.example.myapplication.models.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.models.entity.HabitEntity

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): LiveData<List<HabitEntity>>

    @Query("SELECT * FROM habit WHERE uid = :id")
    fun getById(id: Int): LiveData<HabitEntity>

    @Insert
    fun insert(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabits(habits: List<HabitEntity>)

    @Update
    fun update(habit: HabitEntity)

    @Delete
    fun delete(habit: HabitEntity)
}