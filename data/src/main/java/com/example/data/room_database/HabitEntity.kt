package com.example.data.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("habit")
data class HabitEntity(
    @PrimaryKey var uid: String,
    val name: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val days: Int,
    val times: Int,
    val date: Int
)
