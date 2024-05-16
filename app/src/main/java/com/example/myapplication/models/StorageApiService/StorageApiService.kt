package com.example.myapplication.models.StorageApiService

import com.example.myapplication.models.entity.HabitEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface StorageApiService {
    @GET("habit")
    suspend fun getHabits(): List<HabitEntity>

    @PUT("habit")
    suspend fun postHabit(@Body habit: HabitEntity)

}