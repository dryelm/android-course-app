package com.example.data.room_database.mappers

import com.example.data.room_database.HabitEntity
import com.example.domain.entities.HabitDomainEntity

fun HabitEntity.toDomain(): HabitDomainEntity {
    return HabitDomainEntity(
        uid = this.uid,
        name = this.name,
        description = this.description,
        priority = this.priority,
        type = this.type,
        days = this.days,
        times = this.times,
        date = this.date,
        doneDates = listOf()
    )
}

fun HabitDomainEntity.toEntity(): HabitEntity {
    return HabitEntity(
        uid = this.uid,
        name = this.name,
        description = this.description,
        priority = this.priority,
        type = this.type,
        days = this.days,
        times = this.times,
        date = this.date
    )
}