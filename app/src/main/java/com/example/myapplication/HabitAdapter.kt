package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.bundle_keys.BundleKeys
import com.example.myapplication.fragments.EditHabitFragment

class HabitAdapter(private val habits: ArrayList<Habit>, private val fragment: Fragment) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]

        val nameTextView = holder.view.findViewById<TextView>(R.id.nameTextView)
        val descriptionTextView = holder.view.findViewById<TextView>(R.id.descriptionTextView)
        val priorityTextView = holder.view.findViewById<TextView>(R.id.priorityTextView)
        val typeTextView = holder.view.findViewById<TextView>(R.id.typeTextView)
        val frequencyTextView = holder.view.findViewById<TextView>(R.id.frequencyTextView)

        holder.view.setOnClickListener {
            val bundle = Bundle().apply {
                putInt(BundleKeys.index, position)
            }
            val newFragment = EditHabitFragment.newInstance(bundle)
            fragment.requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, newFragment)
                .addToBackStack(null)
                .commit()
        }

        nameTextView.text = habit.name
        descriptionTextView.text = habit.description
        priorityTextView.text = habit.priority
        typeTextView.text = habit.type
        frequencyTextView.text = holder.itemView.context.getString(R.string.repeats_and_days_view).format(habit.times, habit.days)
    }


    override fun getItemCount() = habits.size
}