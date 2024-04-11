package com.example.myapplication.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Habit
import com.example.myapplication.models.HabitsModel

class EditHabitViewModel : ViewModel() {
    private val mutableHabitList: MutableLiveData<List<Habit>?> = MutableLiveData()

    private val habitsModel: HabitsModel = HabitsModel.getInstance()

    init {
       loadHabits()
    }
    private fun loadHabits(){
        mutableHabitList.value = habitsModel.getAll()
    }
    fun getHabit(index: Int) = mutableHabitList.value?.get(index)

    fun saveHabit(habit: Habit) {
        habitsModel.add(habit)
    }

    fun editHabit(index: Int, habit: Habit) {
        habitsModel.replaceByIndex(index, habit)
    }
}
