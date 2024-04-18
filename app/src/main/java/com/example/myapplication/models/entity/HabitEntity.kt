package com.example.myapplication.models.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.Habit

@Entity("habit")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    val name: String,
    val description: String,
    val priority: String,
    val type: String,
    val days: Int,
    val times: Int
) {
    companion object {
        fun toEntity(habit: Habit): HabitEntity = HabitEntity(
                uid = habit.id,
                name = habit.name,
                description = habit.description,
                priority = habit.priority,
                type = habit.type,
                days = habit.days,
                times = habit.times
            )
    }

}
