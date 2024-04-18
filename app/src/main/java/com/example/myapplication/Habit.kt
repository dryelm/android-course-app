package com.example.myapplication

import android.os.Parcelable
import com.example.myapplication.models.entity.HabitEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(
    val id: Int? = null,
    val name: String,
    val description: String,
    val priority: String,
    val type: String,
    val days: Int,
    val times: Int
) : Parcelable {
    companion object{
        fun fromStorageEntity(habitEntity: HabitEntity): Habit =
            Habit(
                habitEntity.uid,
                habitEntity.name,
                habitEntity.description,
                habitEntity.priority,
                habitEntity.type,
                habitEntity.days,
                habitEntity.times
            )
    }
}