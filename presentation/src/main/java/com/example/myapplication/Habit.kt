package com.example.myapplication

import android.os.Parcelable
import com.example.domain.entities.HabitAddDto
import com.example.domain.entities.HabitDomainEntity
import kotlinx.parcelize.Parcelize
import java.util.Date
import java.util.UUID

@Parcelize
data class Habit(
    val id: String? = null,
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val days: Int,
    val times: Int,
    val date: Date,
    val doneDates: List<Date>
) : Parcelable {
    companion object{
        fun fromStorageEntity(habitEntity: HabitDomainEntity): Habit =
            Habit(
                habitEntity.uid,
                habitEntity.name,
                habitEntity.description,
                HabitPriority.fromValue(habitEntity.priority)!!,
                HabitType.fromValue(habitEntity.type)!!,
                habitEntity.days,
                habitEntity.times,
                Date(habitEntity.date.toLong()),
                habitEntity.doneDates.map { Date(it.toLong()) }
            )
        fun toEntity(habit: Habit): HabitDomainEntity = HabitDomainEntity(
            uid = habit.id ?: UUID.randomUUID().toString(),
            name = habit.name,
            description = habit.description,
            priority = habit.priority.value,
            type = habit.type.value,
            days = habit.days,
            times = habit.times,
            date = habit.date.time.toInt(),
            doneDates = habit.doneDates.map { it.time.toInt() }
        )

        fun toEntity(habit: Habit, uid: String): HabitDomainEntity= HabitDomainEntity(
            uid = uid,
            name = habit.name,
            description = habit.description,
            priority = habit.priority.value,
            type = habit.type.value,
            days = habit.days,
            times = habit.times,
            date = habit.date.time.toInt(),
            doneDates = habit.doneDates.map { it.time.toInt() }
        )

        fun toAddDto(habit: Habit): HabitAddDto = HabitAddDto(
            name = habit.name,
            description = habit.description,
            priority = habit.priority.value,
            type = habit.type.value,
            days = habit.days,
            times = habit.times,
            date = habit.date.time.toInt()
        )
    }

}

enum class HabitType(val value: Int, val stringView: String){
    BAD(0, "Bad"),
    GOOD(1, "Good");
    companion object {
        fun fromValue(value: Int): HabitType? {
            return entries.find { it.value == value }
        }
    }
}

enum class HabitPriority(val value: Int, val stringView: String) {
    LOW(0, "Low"),
    MEDIUM(1, "Medium"),
    HIGH(2, "High");
    companion object {
        fun fromValue(value: Int): HabitPriority? {
            return entries.find { it.value == value }
        }
    }
}