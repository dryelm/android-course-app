package com.example.domain.entities

import com.google.gson.annotations.SerializedName

data class HabitDomainEntity(
    val uid: String,
    @SerializedName("title") val name: String,
    val description: String,
    val priority: Int,
    val type: Int,
    @SerializedName("frequency") val days: Int,
    @SerializedName("count") val times: Int,
    val date: Int,
    val doneDates: List<Int>
)
