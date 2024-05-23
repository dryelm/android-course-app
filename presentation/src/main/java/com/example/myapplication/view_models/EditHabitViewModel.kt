package com.example.myapplication.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Habit
import com.example.data.room_database.HabitsModel
import com.example.domain.HabitsRepository
import com.example.myapplication.HabitsTracker
import kotlinx.coroutines.*

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()

    val habitLiveData: LiveData<Habit?> = mutableHabit

    private val habitsRepository: HabitsRepository = (application as HabitsTracker).applicationComponent.getHabitsRepository()

    private fun loadHabits(lifecycleOwner: LifecycleOwner, id: String?){
        if (id != null) {
            viewModelScope.launch { Dispatchers.IO {
                habitsRepository.getHabitsFromServerAndStoreLocally()
            }}
            val liveData = habitsRepository.getById(id).asLiveData()
            liveData.observe(lifecycleOwner) {
                mutableHabit.value = liveData.value?.let { Habit.fromStorageEntity(it) }
            }
        }
    }

    fun saveHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.add(Habit.toAddDto(habit))
        }
    }

    fun editHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO)  {
            habitsRepository.update(Habit.toEntity(habit))
        }
    }

    fun onViewCreated(lifecycleOwner: LifecycleOwner, id: String?) {
        loadHabits(lifecycleOwner, id)
    }
}
