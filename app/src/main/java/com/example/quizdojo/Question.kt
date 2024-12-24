package com.example.quizdojo

// Question data class
data class Question(
    val question: String,
    val options: List<String>,
    val correctOption: Int
)