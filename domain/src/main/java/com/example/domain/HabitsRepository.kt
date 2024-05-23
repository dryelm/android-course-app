package com.example.domain

import com.example.domain.entities.HabitAddDto
import com.example.domain.entities.HabitDomainEntity
import com.example.domain.entities.HabitUidDto
import kotlinx.coroutines.flow.Flow

interface HabitsRepository {

    suspend fun add(habitAddDto: HabitAddDto)

    fun getAll(): Flow<List<HabitDomainEntity>>

    fun getById(id: String): Flow<HabitDomainEntity>

    suspend fun update(habit: HabitDomainEntity)

    suspend fun getHabitsFromServerAndStoreLocally()

    suspend fun postHabitToServer(habit: HabitAddDto): HabitUidDto?

}