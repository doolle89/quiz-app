package com.example.quizapp.repository.model

data class Question(
    val type: String,
    val question: String,
    val time: Int,
    val points: Boolean,
    val pointsMultiplier: Int,
    val choices: List<Choice>,
    val image: String,
    val resources: String,
    val questionFormat: Int
)