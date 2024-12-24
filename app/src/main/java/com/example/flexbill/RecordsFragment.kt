package com.example.flexbill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class RecordsFragment(private val paidBills: List<String>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_records, container, false)

        val recordsListView = view.findViewById<ListView>(R.id.recordsListView)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, paidBills)
        recordsListView.adapter = adapter

        return view
    }
}