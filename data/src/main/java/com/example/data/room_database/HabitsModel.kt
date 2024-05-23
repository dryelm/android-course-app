package com.example.data.room_database

import com.example.data.room_database.mappers.toDomain
import com.example.data.room_database.mappers.toEntity
import com.example.data.room_database.storageApiService.StorageApiService
import com.example.domain.HabitsRepository
import com.example.domain.entities.HabitAddDto
import com.example.domain.entities.HabitDomainEntity
import com.example.domain.entities.HabitUidDto
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class HabitsModel(private val habitDao: HabitDao,
                               private val storageApiService: StorageApiService): HabitsRepository {


    override suspend fun add(habitAddDto: HabitAddDto){
        val habitUidDto = postHabitToServer(habitAddDto)

        val habitDomainEntity = habitAddDto.toEntity(habitUidDto?.uid ?: UUID.randomUUID().toString())
        habitDao.insert(habitDomainEntity.toEntity())

    }

    override fun getAll(): Flow<List<HabitDomainEntity>> {
        return habitDao.getAll().map { entities -> entities.map { it.toDomain() } }
    }

    override fun getById(id: String): Flow<HabitDomainEntity> {
        return habitDao.getById(id).map { it.toDomain() }
    }

    override suspend fun update(habit: HabitDomainEntity){
        habitDao.update(habit.toEntity())
    }

    override suspend fun getHabitsFromServerAndStoreLocally() {
        val habits = retryOnFailure {
            storageApiService.getHabits()
        }

        habitDao.insertHabits(habits.map { it.toEntity() })
    }

    override suspend fun postHabitToServer(habit: HabitAddDto): HabitUidDto? {
        var habitUid: HabitUidDto? = null
        retryOnFailure {
            habitUid = storageApiService.postHabit(habit)
        }

        return habitUid
    }

    private suspend fun <T> retryOnFailure(

        maxAttempts: Int = 5,
        delayMillis: Long = 100L,
        block: suspend () -> T

    ): T {
        repeat(maxAttempts) {
            try {
                return block()
            } catch (e: Exception) {
                delay(delayMillis)
            }
        }

        return block()

    }

}