package com.example.flexbill

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val paidBills = mutableListOf<String>() // List to store records of paid bills

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set default fragment to Bill Calculator
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, BillCalculatorFragment())
            .commit()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_calculator -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, BillCalculatorFragment())
                        .commit()
                }
                R.id.nav_records -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, RecordsFragment(paidBills))
                        .commit()
                }
            }
            true
        }
    }
}