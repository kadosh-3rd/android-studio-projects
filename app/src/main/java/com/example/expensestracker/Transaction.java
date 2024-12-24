package com.example.expensestracker;

// A simple model class to represent a transaction
// It encapsulates the name and amount of each transaction
public class Transaction {
    private final String name;  // Name or description of the transaction
    private final double amount; // Amount spent for the transaction

    // Constructor to initialize the expense with a name and amount
    public Transaction(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    // Getter method to retrieve the name of the transaction
    public String getName() {
        return name;
    }

    // Getter method to retrieve the amount of the transaction
    public double getAmount() {
        return amount;
    }
}


