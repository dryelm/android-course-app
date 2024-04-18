package com.example.myapplication.view_models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.Habit
import com.example.myapplication.models.HabitsModel

class EditHabitViewModel(application: Application) : AndroidViewModel(application) {
    private val mutableHabit: MutableLiveData<Habit?> = MutableLiveData()

    val habitLiveData: LiveData<Habit?> = mutableHabit

    private val habitsModel: HabitsModel = HabitsModel.getInstance(application)

    private fun loadHabits(lifecycleOwner: LifecycleOwner, id: Int?){
        if (id != null) {
            val liveData = habitsModel.getById(id)
            liveData.observe(lifecycleOwner) {

                mutableHabit.value = liveData.value?.let { Habit.fromStorageEntity(it) }
            }
        }
    }

    fun saveHabit(habit: Habit) {
        habitsModel.add(habit)
    }

    fun editHabit(habit: Habit) {
        habitsModel.update(habit)
    }

    fun onViewCreated(lifecycleOwner: LifecycleOwner, id: Int?) {
        loadHabits(lifecycleOwner, id)
    }
}
