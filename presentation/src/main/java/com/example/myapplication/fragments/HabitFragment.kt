package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.presentation_entities.Habit
import com.example.myapplication.fragments.MainFragment.HabitAdapter
import com.example.myapplication.R
import com.example.myapplication.bundle_keys.BundleKeys

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

        habits = arguments?.getParcelableArrayList(BundleKeys.habits) ?: ArrayList()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = HabitAdapter(habits, this)
    }

    companion object {
        fun newInstance(habits: List<Habit>) = HabitFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(BundleKeys.habits, ArrayList(habits))
            }
        }
    }
}