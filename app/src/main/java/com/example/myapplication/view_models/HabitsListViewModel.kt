package com.example.myapplication.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Habit
import com.example.domain.HabitsRepository
import com.example.myapplication.HabitsTracker
import kotlinx.coroutines.launch

class HabitsListViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableHabitList: MutableLiveData<List<Habit>?> = MutableLiveData()

    val habitList: LiveData<List<Habit>?> = mutableHabitList

    private val habitsRepository: HabitsRepository = (application as HabitsTracker).applicationComponent.getHabitsRepository()


    private fun loadHabits(lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            val habitsLiveData = habitsRepository.getAll().asLiveData()
            habitsLiveData.observe(lifecycleOwner) {
                mutableHabitList.value = habitsLiveData.value?.map { Habit.fromStorageEntity(it) }
            }
        }
    }

    fun onSearchHabits(toSearch: String, lifecycleOwner: LifecycleOwner) {
        Log.d("onSearch", toSearch)

        viewModelScope.launch {
            val habitsLiveData = habitsRepository.getAll().asLiveData()
            habitsLiveData.observe(lifecycleOwner) {
                mutableHabitList.value = habitsLiveData.value?.map { Habit.fromStorageEntity(it) }?.filter {toSearch.isEmpty() || toSearch.isBlank()
                        || it.description.contains(toSearch, true)
                        || it.name.contains(toSearch, true)
                }
            }
        }
    }

    fun onViewCreated(lifecycleOwner: LifecycleOwner) {
        loadHabits(lifecycleOwner)
    }

}