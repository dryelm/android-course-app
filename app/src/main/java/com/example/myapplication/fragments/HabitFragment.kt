package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Habit
import com.example.myapplication.HabitAdapter
import com.example.myapplication.R

class HabitFragment : Fragment() {
    private var habits: ArrayList<Habit> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.view_habit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habits = arguments?.getParcelableArrayList("habits") ?: ArrayList()
        Log.d(this.toString(), habits.toString())

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = HabitAdapter(habits, this)
    }

    companion object {
        fun newInstance(habits: List<Habit>) = HabitFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList("habits", ArrayList(habits))
            }
        }
    }
}