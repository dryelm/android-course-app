package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner

class EditHabitActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val habits = intent.getParcelableArrayListExtra("habits", Habit::class.java) ?: ArrayList()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_habit_activity)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val descriptionEditText = findViewById<EditText>(R.id.descriptionEditText)
        val prioritySpinner = findViewById<Spinner>(R.id.prioritySpinner)
        val countOfRepeats = findViewById<EditText>(R.id.countOfRepeats)
        val daysForHabit = findViewById<EditText>(R.id.daysForHabit)

        ArrayAdapter.createFromResource(
            this,
            R.array.priorities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }

        val typeRadioGroup = findViewById<RadioGroup>(R.id.typeRadioGroup)

        if (intent.hasExtra("index")){
            val habit = habits[intent.getIntExtra("index", -1)]
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
        val saveButton = findViewById<Button>(R.id.saveButton)

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val priority = prioritySpinner.selectedItem?.toString() ?: "Medium"
            val type = when (typeRadioGroup.checkedRadioButtonId) {
                R.id.goodRadioButton -> "Good"
                R.id.badRadioButton -> "Bad"
                else -> ""
            }

            val habit = Habit(name, description, priority, type, daysForHabit.text.toString().toInt(), countOfRepeats.text.toString().toInt())



            val intentToSend = Intent(this, MainActivity::class.java)
            if (intent.hasExtra("index")) {
                habits[intent.getIntExtra("index", -1)] = habit
            }
            else {
                habits.add(habit)
            }
            intentToSend.putParcelableArrayListExtra("habits", habits)
            startActivity(intentToSend)
        }
    }
}