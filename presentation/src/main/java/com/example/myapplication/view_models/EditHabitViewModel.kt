package com.example.myapplication.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.myapplication.presentation_entities.Habit
import com.example.domain.HabitsRepository
import com.example.myapplication.HabitsTracker
import com.example.myapplication.bundle_keys.BundleKeys
import kotlinx.coroutines.*

class EditHabitViewModel(application: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {
    private val habitsRepository: HabitsRepository = (application as HabitsTracker).applicationComponent.getHabitsRepository()

    val habitLiveData: LiveData<Habit?>? =
        savedStateHandle.get<String>(BundleKeys.id)
            ?.let { habitsRepository.getById(it).asLiveData().map { Habit.fromStorageEntity(it) } }

    fun addHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.add(Habit.toAddDto(habit))
        }
    }

    fun editHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO)  {
            habitsRepository.update(Habit.toEntity(habit))
        }
    }
}
