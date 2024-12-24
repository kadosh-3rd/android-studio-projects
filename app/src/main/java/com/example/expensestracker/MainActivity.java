package com.example.expensestracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declare UI components
    private EditText expenseNameInput, expenseAmountInput, initialAmountInput;
    private TextView totalExpenseView, remainingAmountView;

    // Data structure to store expenses and money entries
    private ArrayList<Transaction> expenses;
    private ArrayList<Transaction> moneyEntries;
    private TransactionAdapter expenseAdapter;
    private TransactionAdapter moneyAdapter;
    private double initialAmount = 0.0; // Holds the initial amount or balance
    private final String[] items = new String[]{"Choose the type of income", "Salary", "Investment", "Gift", "Donation", "Other"};
    String selectedItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components by mapping them to layout views
        expenseNameInput = findViewById(R.id.expenseNameInput);
        expenseAmountInput = findViewById(R.id.expenseAmountInput);
        Spinner spinner = findViewById(R.id.dropdownMenu);
        initialAmountInput = findViewById(R.id.initialAmountInput);
        Button addExpenseButton = findViewById(R.id.addExpenseButton);
        Button addMoneyButton = findViewById(R.id.addMoneyButton);
        ListView expenseListView = findViewById(R.id.expenseListView);
        ListView moneyListView = findViewById(R.id.moneyListView);
        totalExpenseView = findViewById(R.id.totalExpenseView);
        remainingAmountView = findViewById(R.id.remainingAmountView);

        // Array adapter for the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        // Initialize the lists of expenses and money entries
        expenses = new ArrayList<>();
        moneyEntries = new ArrayList<>();

        // Initialize adapters for ListViews
        expenseAdapter = new TransactionAdapter(this, expenses);
        moneyAdapter = new TransactionAdapter(this, moneyEntries);

        expenseListView.setAdapter(expenseAdapter);
        moneyListView.setAdapter(moneyAdapter);

        // Set a click listener for the Add Expense button
        addExpenseButton.setOnClickListener(v -> {
            addExpense(); // Call method to add a new expense
        });

        // Set a click listener for the Add Income button
        addMoneyButton.setOnClickListener(v -> {
            addIncome(); // Call method to add money to the balance
        });

        // Set an item selected listener for the Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Method to add a new expense to the list
    private void addExpense() {
        // Get input values from the text fields
        String name = expenseNameInput.getText().toString().trim();
        String amountStr = expenseAmountInput.getText().toString().trim();

        // Validate inputs: check if any field is empty
        if (name.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return; // Exit the method if validation fails
        }

        try {
            // Convert the amount to a double
            double amount = Double.parseDouble(amountStr);

            // Check if there is enough remaining balance
            if (amount > initialAmount) {
                Toast.makeText(this, "Insufficient funds", Toast.LENGTH_SHORT).show();
                return; // Exit if balance is insufficient
            }

            // Deduct the expense amount from the initial amount
            initialAmount -= amount;

            // Add a new Expense object to the list
            expenses.add(new Transaction(name, amount));
            expenseAdapter.notifyDataSetChanged(); // Notify adapter of data changes to update UI

            // Update the displayed totals and remaining balance
            updateTotalExpense();
            updateRemainingAmount();

            // Clear the input fields after successfully adding an expense
            expenseNameInput.setText("");
            expenseAmountInput.setText("");
        } catch (NumberFormatException e) {
            // Handle cases where the amount input is not a valid number
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to add income to the account
    private void addIncome() {
        String amountStr = initialAmountInput.getText().toString().trim();

        // Validate input
        if (amountStr.isEmpty() || selectedItem.isEmpty() || selectedItem.equals("Choose the type of income")) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return; // Exit the method if input is empty
        }

        try {
            // Convert the input to a double and add to the initial amount
            double amount = Double.parseDouble(amountStr);
            initialAmount += amount;

            // Add the money entry to the list
            moneyEntries.add(new Transaction(selectedItem, amount));
            moneyAdapter.notifyDataSetChanged(); // Notify adapter of data changes to update UI

            // Update the remaining balance display
            updateRemainingAmount();

            // Clear the input field
            selectedItem = "";
            initialAmountInput.setText("");
        } catch (NumberFormatException e) {
            // Handle invalid number input
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to calculate and display the total of all expenses
    @SuppressLint("DefaultLocale")
    private void updateTotalExpense() {
        double total = 0;
        // Loop through the list of expenses to calculate the total amount
        for (Transaction expense : expenses) {
            total += expense.getAmount();
        }
        // Display the total expense with a formatted string
        totalExpenseView.setText(String.format("Total Spent: $%.2f", total));
    }

    // Method to update and display the remaining balance
    @SuppressLint("DefaultLocale")
    private void updateRemainingAmount() {
        remainingAmountView.setText(String.format("Remaining Balance: $%.2f", initialAmount));
    }
}
