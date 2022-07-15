package com.example.quizapp.repository.model


data class Kahoot(
    val uuid: String,
    val title: String,
    val description: String,
    val quizType: String,
    val cover: String,
    val questions: List<Question>,
    val type: String
)
