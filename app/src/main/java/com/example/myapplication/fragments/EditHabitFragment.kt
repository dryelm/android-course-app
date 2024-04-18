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
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Habit
import com.example.myapplication.R
import com.example.myapplication.bundle_keys.BundleKeys
import com.example.myapplication.view_models.EditHabitViewModel

class EditHabitFragment : Fragment() {
    private var id: Int? = null
    private lateinit var viewModel: EditHabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[EditHabitViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.edit_habit_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments?.containsKey(BundleKeys.id) == true) {
            id = arguments?.getInt(BundleKeys.id)
            Log.d(this.toString(), id.toString())
        }

        viewModel.onViewCreated(viewLifecycleOwner, id)

        configurePrioritySpinner(view)
        fillFieldsWhenEdit(view)
        configureButton(view)
    }

    private fun configurePrioritySpinner(view: View) {
        val prioritySpinner = view.findViewById<Spinner>(R.id.prioritySpinner)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.priorities,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }
    }



    private fun fillFieldsWhenEdit(view: View) {
        if (id != null) {
            val nameEditText = view.findViewById<AppCompatEditText>(R.id.nameEditText)
            val descriptionEditText = view.findViewById<AppCompatEditText>(R.id.descriptionEditText)
            val prioritySpinner = view.findViewById<Spinner>(R.id.prioritySpinner)
            val countOfRepeats = view.findViewById<AppCompatEditText>(R.id.countOfRepeats)
            val daysForHabit = view.findViewById<AppCompatEditText>(R.id.daysForHabit)
            val typeRadioGroup = view.findViewById<RadioGroup>(R.id.typeRadioGroup
            )
            id?.let {
                val habitLiveData = viewModel.habitLiveData
                habitLiveData.observe(viewLifecycleOwner) {
                    val habit = it
                    Log.d(this.toString(), habit.toString())
                    nameEditText.setText(habit?.name)
                    descriptionEditText.setText(habit?.description)
                    prioritySpinner.setSelection(
                        when (habit?.priority) {
                            getString(R.string.low_priority_word) -> 0
                            getString(R.string.medium_priority_word) -> 1
                            getString(R.string.high_priority_word) -> 2
                            else -> -1
                        }
                    )

                    when (habit?.type) {
                        getString(R.string.good_habit_word) -> typeRadioGroup.check(R.id.goodRadioButton)
                        getString(R.string.bad_habit_word) -> typeRadioGroup.check(R.id.badRadioButton)
                    }

                    countOfRepeats.setText(habit?.times.toString())
                    daysForHabit.setText(habit?.days.toString())
                }
            }
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
            nameEditText?.error = getString(R.string.blank_name_habit_error)
            isError = true
        }
        if (descriptionEditText?.text.isNullOrBlank()){
            descriptionEditText?.error = getString(R.string.blank_description_habit_error)
            isError = true
        }
        if (typeRadioGroup?.checkedRadioButtonId == -1){
            Toast.makeText(context, getString(R.string.blank_habit_type_error), Toast.LENGTH_SHORT).show()
            isError = true
        }
        if (prioritySpinner?.selectedItem == null){
            Toast.makeText(context,
                getString(R.string.blank_habit_priority_error), Toast.LENGTH_SHORT).show()
            isError = true
        }
        if (daysForHabit?.text.isNullOrBlank()){
            daysForHabit?.error = getString(R.string.blank_days_habit_error)
            isError = true
        }
        if (countOfRepeats?.text.isNullOrBlank()){
            countOfRepeats?.error = getString(R.string.blank_repeats_habit_error)
            isError = true
        }

        return isError
    }

    private fun configureButton(
        view: View
    ) {
        val saveButton = view.findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val nameEditText = view.findViewById<AppCompatEditText>(R.id.nameEditText)
            val descriptionEditText = view.findViewById<AppCompatEditText>(R.id.descriptionEditText)
            val prioritySpinner = view.findViewById<Spinner>(R.id.prioritySpinner)
            val countOfRepeats = view.findViewById<AppCompatEditText>(R.id.countOfRepeats)
            val daysForHabit = view.findViewById<AppCompatEditText>(R.id.daysForHabit)
            val typeRadioGroup = view.findViewById<RadioGroup>(R.id.typeRadioGroup)
            if (isFieldsWithError(
                    nameEditText,
                    descriptionEditText,
                    typeRadioGroup,
                    prioritySpinner,
                    daysForHabit,
                    countOfRepeats
                )
            ) {
                return@setOnClickListener
            }

            val name = nameEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val priority =
                prioritySpinner.selectedItem?.toString() ?: getString(R.string.medium_priority_word)
            val type = when (typeRadioGroup.checkedRadioButtonId) {
                R.id.goodRadioButton -> getString(R.string.good_habit_word)
                R.id.badRadioButton -> getString(R.string.bad_habit_word)
                else -> ""
            }


            val habit = Habit(
                id,
                name,
                description,
                priority,
                type,
                daysForHabit.text.toString().toInt(),
                countOfRepeats.text.toString().toInt()
            )

            if (id != null) {
                viewModel.editHabit(habit)
            } else {
                viewModel.saveHabit(habit)
            }

            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle) = EditHabitFragment().apply {
            arguments = bundle
        }

        fun newInstance() = EditHabitFragment()
    }
}