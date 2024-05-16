package com.example.myapplication.models

import HabitEntityDeserializer
import HabitEntitySerializer
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.myapplication.Habit
import com.example.myapplication.models.StorageApiService.StorageApiService
import com.example.myapplication.models.dao.HabitDao
import com.example.myapplication.models.database.AppDatabase
import com.example.myapplication.models.entity.HabitEntity
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HabitsModel private constructor(context: Context) {
    companion object {
        @Volatile
        private var instance: HabitsModel? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: HabitsModel(context).also { instance = it }
            }
    }

    private val habitDao: HabitDao
    private val storageApiService: StorageApiService

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder().apply {

            addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("accept", "application/json")
                    .header("Authorization", "2b214865-0395-4cdf-88a7-621ac8b10325")
                    .method(original.method, original.body)
                val request = requestBuilder.build()

                chain.proceed(request)
            })
            addInterceptor(loggingInterceptor)
        }.build()

        val gson = GsonBuilder()
            .registerTypeAdapter(HabitEntity::class.java, HabitEntitySerializer())
            .registerTypeAdapter(HabitEntity::class.java, HabitEntityDeserializer())
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient)
            .build()

        storageApiService = retrofit.create(StorageApiService::class.java)

        val db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "habit"
        ).fallbackToDestructiveMigration().build()
        habitDao = db.habitDao()
    }

    suspend fun add(habit: Habit){
        habitDao.insert(HabitEntity.toEntity(habit))
        postHabitToServer(HabitEntity.toEntity(habit))
    }

    fun getAll(): LiveData<List<HabitEntity>> {
        return habitDao.getAll()
    }

    fun getById(id: Int): LiveData<HabitEntity> {
        return habitDao.getById(id)
    }

    suspend fun update(habit: Habit){
        habitDao.update(HabitEntity.toEntity(habit))
    }

    suspend fun getHabitsFromServerAndStoreLocally() {
        val habits = retryOnFailure {
            storageApiService.getHabits()
        }
        habitDao.insertHabits(habits)
    }

    suspend fun postHabitToServer(habit: HabitEntity) {
        retryOnFailure {
            storageApiService.postHabit(habit)
        }
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