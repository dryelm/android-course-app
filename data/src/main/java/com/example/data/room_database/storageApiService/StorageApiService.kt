package com.example.data.room_database.storageApiService

import com.example.domain.entities.HabitAddDto
import com.example.domain.entities.HabitDomainEntity
import com.example.domain.entities.HabitUidDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface StorageApiService {
    @GET("habit")
    suspend fun getHabits(): List<HabitDomainEntity>

    @PUT("habit")
    suspend fun postHabit(@Body habit: HabitAddDto): HabitUidDto

}