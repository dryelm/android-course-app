package com.example.myapplication.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.Habit
import com.example.myapplication.models.HabitsModel

class HabitsListViewModel : ViewModel() {
    private val mutableHabitList: MutableLiveData<List<Habit>?> = MutableLiveData()

    val habitList: LiveData<List<Habit>?> = mutableHabitList

    private val habitsModel: HabitsModel = HabitsModel.getInstance()

    private fun loadHabits() {
        mutableHabitList.value = habitsModel.getAll()
    }

    fun onSearchHabits(toSearch: String) {
        Log.d("onSearch", toSearch)
        mutableHabitList.value = habitsModel.filter {toSearch.isBlank()
                    || it.description.contains(toSearch, true)
                    || it.name.contains(toSearch, true)
        }
    }

    fun onViewCreated() {
        loadHabits()
    }

}