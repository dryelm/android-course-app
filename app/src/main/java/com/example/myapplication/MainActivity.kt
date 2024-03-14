package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.versionedparcelable.ParcelField
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.parcelize.Parcelize

@Parcelize
data class Habit(val name: String, val description: String, val priority: String, val type: String, val days: Int, val times: Int) : Parcelable

enum class HabitPriority(val priority: String){
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High"),
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val habits = intent.getParcelableArrayListExtra("habits", Habit::class.java)
            ?: ArrayList<Habit>()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HabitAdapter(habits)
        Log.d("MainActivity", habits.joinToString())

        val addButton = findViewById<FloatingActionButton>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, EditHabitActivity::class.java)
            intent.putParcelableArrayListExtra("habits", habits)
            startActivity(intent)
        }
    }
}

class HabitAdapter(private val habits: ArrayList<Habit>) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

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
            val context = holder.view.context
            val intent = Intent(context, EditHabitActivity::class.java)
            intent.putParcelableArrayListExtra("habits", habits)
            intent.putExtra("index", position)
            context.startActivity(intent)
        }

        nameTextView.text = habit.name
        descriptionTextView.text = habit.description
        priorityTextView.text = habit.priority
        typeTextView.text = habit.type
        frequencyTextView.setText("${habit.times} раз. Через каждые ${habit.days} дней(день)");
    }


    override fun getItemCount() = habits.size
}