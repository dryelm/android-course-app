package com.example.domain.entities

import com.google.gson.annotations.SerializedName

data class HabitAddDto(
    @SerializedName("title") val name: String,
    val description: String,
    val priority: Int,
    val type: Int,
    @SerializedName("frequency") val days: Int,
    @SerializedName("count") val times: Int,
    val date: Int
) {
    fun toEntity(uid: String): HabitDomainEntity = HabitDomainEntity(
        uid = uid,
        name = name,
        description = description,
        priority = priority,
        type = type,
        days = days,
        times = times,
        date = date,
        doneDates = listOf()
    )
}