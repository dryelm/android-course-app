package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {
    var number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        number = intent.getIntExtra("number", 0)
        val mes = "Square of number: ${number * number}";
        Log.d("SecondActivity", mes)

        val textView = findViewById<TextView>(R.id.number_text)
        textView.text = mes
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java )
            intent.putExtra("number", number)
            startActivity(intent)
        }
    }
}