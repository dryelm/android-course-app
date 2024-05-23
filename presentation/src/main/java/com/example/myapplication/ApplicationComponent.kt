package com.example.myapplication

import android.content.Context
import com.example.domain.HabitsRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface ApplicationComponent {
    fun getHabitsRepository(): HabitsRepository


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): ApplicationComponent
    }

}
