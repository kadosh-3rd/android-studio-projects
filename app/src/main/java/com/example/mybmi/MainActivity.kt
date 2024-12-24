package com.example.mybmi

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.textfield.TextInputLayout
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    // Declare UI components as class-level properties
    private lateinit var weightEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var categoryTextView: TextView
    private lateinit var adviceTextView: TextView
    private lateinit var resultsCardView: CardView
    private lateinit var heightInputLayout: TextInputLayout
    private lateinit var weightInputLayout: TextInputLayout

    // Constants for validation
    companion object {
        const val MAX_HEIGHT = 2.6 // Maximum allowed height in meters
        const val MAX_WEIGHT = 700.0 // Maximum allowed weight in kg
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the activity
        setContentView(R.layout.activity_main)

        // Initialize UI components by finding their views
        weightEditText = findViewById(R.id.weightEditText)
        heightEditText = findViewById(R.id.heightEditText)
        calculateButton = findViewById(R.id.calculateButton)
        resultTextView = findViewById(R.id.resultTextView)
        categoryTextView = findViewById(R.id.categoryTextView)
        adviceTextView = findViewById(R.id.adviceTextView)
        resultsCardView = findViewById(R.id.resultsCardView)
        heightInputLayout = findViewById(R.id.heightInputLayout)
        weightInputLayout = findViewById(R.id.weightInputLayout)

        // Initially hide the results card
        resultsCardView.visibility = CardView.GONE

        // Set focus change listeners for validation on input changes
        heightEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateHeight()
            }
        }
        weightEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validateWeight()
            }
        }

        // Set click listener for the calculate button
        calculateButton.setOnClickListener {
            if(validateInputs()){
                calculateBMI()
            }
        }
    }

    // Function to validate height input
    private fun validateHeight(): Boolean {
        val heightStr = heightEditText.text.toString()
        if (heightStr.isNotEmpty()) {
            try {
                val height = heightStr.toDouble()
                if (height > MAX_HEIGHT) {
                    heightInputLayout.error = "Height cannot exceed ${MAX_HEIGHT}m"
                    return false
                } else if (height <= 0) {
                    heightInputLayout.error = "Height must be greater than 0"
                    return false
                }
                heightInputLayout.error = null
                return true
            } catch (e: NumberFormatException) {
                heightInputLayout.error = "Invalid height format"
                return false
            }
        } else {
            heightInputLayout.error = "Height is required"
            resultsCardView.visibility = CardView.GONE
            return false
        }
    }

    // Function to validate weight input
    private fun validateWeight(): Boolean {
        val weightStr = weightEditText.text.toString()
        if (weightStr.isNotEmpty()) {
            try {
                val weight = weightStr.toDouble()
                if (weight > MAX_WEIGHT) {
                    weightInputLayout.error = "Weight cannot exceed ${MAX_WEIGHT}kg"
                    return false
                } else if (weight <= 0) {
                    weightInputLayout.error = "Weight must be greater than 0"
                    return false
                }
                weightInputLayout.error = null
                return true
            } catch (e: NumberFormatException) {
                weightInputLayout.error = "Invalid weight format"
                return false
            }
        } else {
            weightInputLayout.error = "Weight is required"
            resultsCardView.visibility = CardView.GONE
            return false
        }
    }

    // Function to validate all inputs
    private fun validateInputs(): Boolean {
        val heightValid = validateHeight()
        val weightValid = validateWeight()
        return heightValid && weightValid
    }

    // Function to calculate and display BMI
    @SuppressLint("SetTextI18n")
    private fun calculateBMI() {
        // Get weight and height from input fields
        val weightStr = weightEditText.text.toString()
        val heightStr = heightEditText.text.toString()

        // Check if inputs are not empty
        if (weightStr.isNotEmpty() && heightStr.isNotEmpty()) {
            try {
                // Convert input strings to doubles
                val weight = weightStr.toDouble()
                val height = heightStr.toDouble()

                // Calculate BMI (weight / (height in meters)^2)
                val bmi = weight / (height * height)

                // Round BMI to two decimal places
                val roundedBMI = (bmi * 100.0).roundToInt() / 100.0

                // Determine BMI category and medical advice
                val (category, advice) = getBMICategoryAndAdvice(bmi)

                // Display results
                resultTextView.text = "BMI: $roundedBMI"
                categoryTextView.text = "Category: $category"
                adviceTextView.text = advice

                // Show the results card
                resultsCardView.visibility = CardView.VISIBLE

            } catch (e: NumberFormatException) {
                // Handle invalid input
                resultTextView.text = "Invalid input"
                categoryTextView.text = ""
                adviceTextView.text = ""
                resultsCardView.visibility = CardView.GONE
            }
        } else {
            // Handle empty input
            resultTextView.text = "Please enter weight and height"
            categoryTextView.text = ""
            adviceTextView.text = ""
            resultsCardView.visibility = CardView.GONE
        }
    }

    // Function to get BMI category and medical advice
    private fun getBMICategoryAndAdvice(bmi: Double): Pair<String, String> {
        return when {
            bmi < 18.5 -> Pair(
                "Underweight",
                "Advice from Dr Divine: Consider consulting a nutritionist. Focus on balanced, nutrient-rich diet and strength training to build muscle mass. Regular medical check-ups are recommended."
            )
            bmi < 25 -> Pair(
                "Normal weight",
                "Advice from Dr Mary: Maintain your current lifestyle. Continue with balanced diet, regular exercise, and preventive health screenings."
            )
            bmi < 30 -> Pair(
                "Overweight",
                "Advice from Dr Wilson: Consult with a healthcare professional. Focus on balanced nutrition, regular physical activity, and potential lifestyle modifications to reduce health risks."
            )
            else -> Pair(
                "Obese",
                "Advice from Dr Joseph: Urgent medical consultation recommended. Develop a comprehensive health plan including diet modification, increased physical activity, and potential medical interventions."
            )
        }
    }
}