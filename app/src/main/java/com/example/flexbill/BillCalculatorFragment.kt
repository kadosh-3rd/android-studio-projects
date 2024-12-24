package com.example.flexbill

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.*

class BillCalculatorFragment : Fragment() {
    private val dishItems = listOf("Choose Dish","Beef & Rice","Chicken & Rice", "Fish & Rice", "Potatoes & Meatballs")
    private val drinkItems = listOf("Choose Drink","Water", "Soda", "Juice", "Tea", "Coffee", "Milk", "Beer")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
savedInstanceState: Bundle?
): View? {
    val view = inflater.inflate(R.layout.fragment_bill_calculator, container, false)

    val billAmountEditText = view.findViewById<EditText>(R.id.billAmountEditText)
        val dishDropdownMenu = view.findViewById<Spinner>(R.id.dishDropdownMenu)
        val drinkDropdownMenu = view.findViewById<Spinner>(R.id.drinkDropdownMenu)
    val tipSeekBar = view.findViewById<SeekBar>(R.id.tipSeekBar)
    val tipPercentageText = view.findViewById<TextView>(R.id.tipPercentageText)
    val totalAmountText = view.findViewById<TextView>(R.id.totalAmountText)
    val payButton = view.findViewById<Button>(R.id.payButton)

        val dishAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dishItems)
        dishAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dishDropdownMenu.adapter = dishAdapter

        val drinkAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, drinkItems)
        drinkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        drinkDropdownMenu.adapter = drinkAdapter

    tipSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        @SuppressLint("SetTextI18n")
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            tipPercentageText.text = "$progress%"
            calculateTotal(billAmountEditText, progress, totalAmountText)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    })

    payButton.setOnClickListener {
        val billAmount = billAmountEditText.text.toString().toDoubleOrNull()
val tipPercentage = tipSeekBar.progress

if (billAmount != null && billAmount > 0) {
    val total = billAmount + (billAmount * tipPercentage / 100)
    (activity as? MainActivity)?.paidBills?.add(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(total))
    Toast.makeText(context, "Payment Successful!", Toast.LENGTH_SHORT).show()
    billAmountEditText.text.clear()
    totalAmountText.text = ""
} else {
    Toast.makeText(context, "Enter a valid bill amount", Toast.LENGTH_SHORT).show()
}
}

return view
}

private fun calculateTotal(
    billAmountEditText: EditText,
    tipPercentage: Int,
    totalAmountText: TextView
) {
    val billAmount = billAmountEditText.text.toString().toDoubleOrNull()
    if (billAmount != null && billAmount > 0) {
        val total = billAmount + (billAmount * tipPercentage / 100)
        totalAmountText.text = NumberFormat.getCurrencyInstance(Locale.getDefault()).format(total)
    } else {
        totalAmountText.text = ""
    }
}
}