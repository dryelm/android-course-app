package com.example.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import com.example.myapplication.Habit
import com.example.myapplication.R

class EditHabitFragment : Fragment() {
    private var habits: ArrayList<Habit> = ArrayList()
    private var index: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.edit_habit_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habits = arguments?.getParcelableArrayList("habits") ?: ArrayList()
        if (arguments?.containsKey("index") == true) {
            index = arguments?.getInt("index")
        }

        val nameEditText = view.findViewById<AppCompatEditText>(R.id.nameEditText)
        val descriptionEditText = view.findViewById<AppCompatEditText>(R.id.descriptionEditText)
        val prioritySpinner = view.findViewById<Spinner>(R.id.prioritySpinner)
        val countOfRepeats = view.findViewById<AppCompatEditText>(R.id.countOfRepeats)
        val daysForHabit = view.findViewById<AppCompatEditText>(R.id.daysForHabit)
        val typeRadioGroup = view.findViewById<RadioGroup>(R.id.typeRadioGroup)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priorities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }
        if (index != null) {
            index?.let {
                val habit = habits[it]
                nameEditText.setText(habit.name)
                descriptionEditText.setText(habit.description)
                prioritySpinner.setSelection(when (habit.priority) {
                    "Low" -> 0
                    "Medium" -> 1
                    "High" -> 2
                    else -> -1
                })

                when (habit.type) {
                    "Good" -> typeRadioGroup.check(R.id.goodRadioButton)
                    "Bad" -> typeRadioGroup.check(R.id.badRadioButton)
                }

                countOfRepeats.setText(habit.times.toString())
                daysForHabit.setText(habit.days.toString())
            }
        }

        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val priority = prioritySpinner.selectedItem?.toString() ?: "Medium"
            val type = when (typeRadioGroup.checkedRadioButtonId) {
                R.id.goodRadioButton -> "Good"
                R.id.badRadioButton -> "Bad"
                else -> ""
            }

            if (isFieldsWithError(nameEditText, descriptionEditText, typeRadioGroup, prioritySpinner, daysForHabit, countOfRepeats)){
                return@setOnClickListener
            }

            val habit = Habit(name, description, priority, type, daysForHabit.text.toString().toInt(), countOfRepeats.text.toString().toInt())

            if (index != null) {
                habits[index!!] = habit
            } else {
                habits.add(habit)
            }

            val bundle = Bundle().apply {
                Log.d(this.toString(), habits.toString())
                putParcelableArrayList("habits", habits)
            }
            val fragment = MainFragment.newInstance(bundle)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun isFieldsWithError(
        nameEditText: AppCompatEditText?,
        descriptionEditText: AppCompatEditText?,
        typeRadioGroup: RadioGroup?,
        prioritySpinner: Spinner?,
        daysForHabit: AppCompatEditText?,
        countOfRepeats: AppCompatEditText?
    ): Boolean{
        var isError = false
        if (nameEditText?.text.isNullOrBlank()){
            nameEditText?.error = "Не написали название"
            isError = true
        }
        if (descriptionEditText?.text.isNullOrBlank()){
            descriptionEditText?.error = "Не написали описание"
            isError = true
        }
        if (typeRadioGroup?.checkedRadioButtonId == -1){
            Toast.makeText(context, "Не выбрали тип привычки", Toast.LENGTH_SHORT).show()
            isError = true
        }
        if (prioritySpinner?.selectedItem == null){
            Toast.makeText(context, "Не выбрали приоритет", Toast.LENGTH_SHORT).show()
            isError = true
        }
        if (daysForHabit?.text.isNullOrBlank()){
            daysForHabit?.error = "Не указали количество дней"
            isError = true
        }
        if (countOfRepeats?.text.isNullOrBlank()){
            countOfRepeats?.error = "Не указали количество повторений"
            isError = true
        }

        return isError
    }

    companion object {
        fun newInstance(bundle: Bundle) = EditHabitFragment().apply {
            arguments = bundle
        }
    }
}