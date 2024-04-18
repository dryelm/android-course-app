package com.example.myapplication.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Habit
import com.example.myapplication.models.HabitsModel

class HabitsListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableHabitList: MutableLiveData<List<Habit>?> = MutableLiveData()

    val habitList: LiveData<List<Habit>?> = mutableHabitList

    private val habitsModel: HabitsModel = HabitsModel.getInstance(application)

    private fun loadHabits(lifecycleOwner: LifecycleOwner) {
        val habitsLiveData = habitsModel.getAll()
        habitsLiveData.observe(lifecycleOwner) {
            mutableHabitList.value = habitsLiveData.value?.map { Habit.fromStorageEntity(it) }
        }
    }

    fun onSearchHabits(toSearch: String, lifecycleOwner: LifecycleOwner) {
        Log.d("onSearch", toSearch)

        val habitsLiveData = habitsModel.getAll()
        habitsLiveData.observe(lifecycleOwner) {
            mutableHabitList.value = habitsLiveData.value?.map { Habit.fromStorageEntity(it) }?.filter {toSearch.isEmpty() || toSearch.isBlank()
                    || it.description.contains(toSearch, true)
                    || it.name.contains(toSearch, true)
        }
        }
    }

    fun onViewCreated(lifecycleOwner: LifecycleOwner) {
        loadHabits(lifecycleOwner)
    }

}