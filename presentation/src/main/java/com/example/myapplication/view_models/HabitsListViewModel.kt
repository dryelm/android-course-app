package com.example.myapplication.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Habit
import com.example.domain.HabitsRepository
import com.example.myapplication.HabitsTracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*

class HabitsListViewModel(application: Application) : AndroidViewModel(application) {

    private val habitsRepository: HabitsRepository =
        (application as HabitsTracker).applicationComponent.getHabitsRepository()

    private val _searchQuery = MutableLiveData("")
    val habitList: LiveData<List<Habit>> = _searchQuery.switchMap { query ->
        habitsRepository.getAll().asLiveData().map { list ->
            list.map { habitDomainEntity -> Habit.fromStorageEntity(habitDomainEntity) }
                .filter {
                    query.isEmpty() || query.isBlank()
                            || it.description.contains(query, true)
                            || it.name.contains(query, true)
                }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            habitsRepository.getHabitsFromServerAndStoreLocally()
        }
    }

    fun onSearchHabits(toSearch: String) {
        Log.d("onSearch", toSearch)
        _searchQuery.postValue(toSearch)
    }
}


