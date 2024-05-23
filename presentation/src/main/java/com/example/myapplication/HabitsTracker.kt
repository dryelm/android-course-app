package com.example.myapplication

import android.app.Application

class HabitsTracker: Application() {
    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .context(this)
            .build()
    }
}