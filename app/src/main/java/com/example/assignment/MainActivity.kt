package com.example.assignment

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentInput = ""
    private var operation: String? = null
    private var firstOperand: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        val buttons = listOf(
            R.id.btnClear, R.id.btnDivide, R.id.btnMultiply,
            R.id.btnDelete, R.id.btnEqual, R.id.btnAdd, R.id.btnSubtract,
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { handleButtonPress(it as Button) }
        }
    }

    private fun handleButtonPress(button: Button) {
        val text = button.text.toString()

        when (text) {
            "C" -> clear()
            "DEL" -> deleteLast()
            "÷", "×", "-", "+" -> setOperation(text)
            "=" -> calculateResult()
            else -> appendNumber(text)
        }
    }

    private fun clear() {
        currentInput = ""
        operation = null
        firstOperand = null
        tvDisplay.text = "0"
    }

    private fun deleteLast() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            tvDisplay.text = currentInput.ifEmpty { "0" }
        }
    }

    private fun setOperation(op: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
            operation = op
            currentInput = ""
        }
    }

    private fun appendNumber(number: String) {
        currentInput += number
        tvDisplay.text = currentInput
    }

    private fun calculateResult() {
        if (firstOperand != null && currentInput.isNotEmpty() && operation != null) {
            val secondOperand = currentInput.toDouble()
            val result = when (operation) {
                "÷" -> firstOperand!! / secondOperand
                "×" -> firstOperand!! * secondOperand
                "-" -> firstOperand!! - secondOperand
                "+" -> firstOperand!! + secondOperand
                else -> 0.0
            }
            tvDisplay.text = result.toString()
            currentInput = result.toString()
            firstOperand = null
            operation = null
        }
    }
}
