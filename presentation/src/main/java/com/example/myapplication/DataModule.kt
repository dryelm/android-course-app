package com.example.myapplication

import android.content.Context
import androidx.room.Room
import com.example.data.room_database.HabitDao
import com.example.data.room_database.HabitsModel
import com.example.data.room_database.storageApiService.StorageApiService
import com.example.domain.HabitsRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideHabitsRepository(habitDao: HabitDao,
                                storageApiService: StorageApiService): HabitsRepository = HabitsModel(habitDao, storageApiService)

    @Provides
    fun provideHabitDao(appDatabase: AppDatabase) = appDatabase.habitDao()
    @Singleton
    @Provides
    fun provideDataBase(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "habit"
        ).fallbackToDestructiveMigration().build()


    @Provides
    fun provideStorageApiService(retrofit: Retrofit): StorageApiService {
        return retrofit.create(StorageApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://droid-test-server.doubletapp.ru/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder().apply {

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
    }


}