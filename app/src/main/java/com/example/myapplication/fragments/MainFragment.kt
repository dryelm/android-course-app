package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.view_models.HabitsListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private lateinit var viewModel: HabitsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HabitsListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bottomSheetFragment = BottomSheetFragment()
        childFragmentManager.beginTransaction().replace(R.id.design_bottom_sheet, bottomSheetFragment)
            .commit()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated()

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)


        viewModel.habitList.observe(viewLifecycleOwner) { habits ->
            val fragments = listOf(
                HabitFragment.newInstance(habits!!.filter { it.type == getString(R.string.good_habit_word) }),
                HabitFragment.newInstance(habits.filter { it.type == getString(R.string.bad_habit_word) })
            )
            activity?.let {
                viewPager.adapter = object : FragmentStateAdapter(this) {
                    override fun getItemCount() = fragments.size
                    override fun createFragment(position: Int) = fragments[position]
                }

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text =
                        if (position == 0) getString(R.string.good_habits_interface_word) else getString(
                            R.string.bad_habits_interface_word
                        )
                }.attach()
            }
        }

        configureAddButton(view)


    }

    private fun configureAddButton(view: View) {
        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            val fragment = EditHabitFragment.newInstance()
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(bundle: Bundle) = MainFragment().apply {
            arguments = bundle
        }
    }
}
