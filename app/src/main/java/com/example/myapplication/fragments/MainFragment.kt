package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Habit
import com.example.myapplication.R
import com.example.myapplication.callbacks.HabitCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {
    private var habits: ArrayList<Habit> = ArrayList()
    var callback: HabitCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habits = arguments?.getParcelableArrayList("habits") ?: ArrayList()

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        val fragments = listOf(
            HabitFragment.newInstance(habits.filter { it.type == "Good" }),
            HabitFragment.newInstance(habits.filter { it.type == "Bad" })
        )
        activity?.let {
            viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position]
        }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = if (position == 0) "Хорошие привычки" else "Плохие привычки"
            }.attach()
        }


        val addButton = view.findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelableArrayList("habits", habits)
            }
            val fragment = EditHabitFragment.newInstance(bundle)
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }



    override fun onDestroy() {
        if (callback != null){
            callback?.onDestroy(habits)
        }
        super.onDestroy()
    }

    companion object {
        fun newInstance(bundle: Bundle) = MainFragment().apply {
            arguments = bundle
        }
    }
}
