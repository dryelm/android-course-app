package com.example.data.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habit")
    fun getAll(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habit WHERE uid = :id")
    fun getById(id: String): Flow<HabitEntity>

    @Insert
    fun insert(habit: HabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHabits(habits: List<HabitEntity>)

    @Update
    fun update(habit: HabitEntity)

    @Delete
    fun delete(habit: HabitEntity)
}