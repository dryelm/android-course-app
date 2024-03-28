package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(val name: String, val description: String, val priority: String, val type: String, val days: Int, val times: Int) :
    Parcelable