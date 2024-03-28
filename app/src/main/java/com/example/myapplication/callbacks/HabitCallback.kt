package com.example.myapplication.callbacks

import com.example.myapplication.Habit

interface HabitCallback {
    fun onDestroy(habits: ArrayList<Habit>)
}