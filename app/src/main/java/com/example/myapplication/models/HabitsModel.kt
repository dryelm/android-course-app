package com.example.myapplication.models

import com.example.myapplication.Habit

class HabitsModel private constructor() {
    companion object {
        @Volatile
        private var instance: HabitsModel? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?:  HabitsModel().also { instance = it }
            }
    }

    private val habitList: MutableList<Habit>  = mutableListOf()

    fun add(habit: Habit){
        habitList.add(habit)
    }

    fun getAll() = habitList.toMutableList()

    fun getByIndex(index: Int) = habitList[index]

    fun replaceByIndex(index: Int, habit: Habit){
        habitList[index] = habit
    }

    fun filter(predicate: (Habit) -> Boolean) = habitList.filter { predicate(it) }
}