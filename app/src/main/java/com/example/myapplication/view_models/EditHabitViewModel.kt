package com.example.myapplication.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Habit
import com.example.myapplication.models.HabitsModel
import kotlinx.coroutines.*

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()

    val habitLiveData: LiveData<Habit?> = mutableHabit

    private val habitsModel: HabitsModel = HabitsModel.getInstance(application)
    private val viewModelJob = Job()

    private fun loadHabits(lifecycleOwner: LifecycleOwner, id: Int?){
        if (id != null) {
            viewModelScope.launch { Dispatchers.IO {
                habitsModel.getHabitsFromServerAndStoreLocally()
            }}
            val liveData = habitsModel.getById(id)
            liveData.observe(lifecycleOwner) {
                mutableHabit.value = liveData.value?.let { Habit.fromStorageEntity(it) }
            }
        }
    }

    fun saveHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO) {
            habitsModel.add(habit)
        }
    }

    fun editHabit(habit: Habit) {
        viewModelScope.launch(Dispatchers.IO)  {
            habitsModel.update(habit)
        }
    }

    fun onViewCreated(lifecycleOwner: LifecycleOwner, id: Int?) {
        loadHabits(lifecycleOwner, id)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
