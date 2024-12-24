package com.example.expensestracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

// Customizes how the list of transactions is displayed in the ListView binding them to the layout
public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private final Context context; // Context of the application or activity
    private final List<Transaction> transactions; // List of Transaction objects to display

    // Constructor to initialize the adapter with context and transaction list
    public TransactionAdapter(Context context, List<Transaction> transactions) {
        super(context, 0, transactions);
        this.context = context;
        this.transactions = transactions;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Reuse an existing view if available, or inflate a new one
        if (convertView == null) {
            // Inflate a simple list item layout with two text views
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        // Get the current Transaction object from the list based on position
        Transaction transaction = transactions.get(position);

        // Find the text views within the list item layout
        TextView nameView = convertView.findViewById(android.R.id.text1); // First text view for the name
        TextView amountView = convertView.findViewById(android.R.id.text2); // Second text view for the amount

        // Set the name and amount data to the respective text views
        nameView.setText(transaction.getName()); // Display the name of the transaction
        amountView.setText(String.format("$%.2f", transaction.getAmount())); // Format and display the amount

        // Return the fully populated list item view
        return convertView;
    }
}
