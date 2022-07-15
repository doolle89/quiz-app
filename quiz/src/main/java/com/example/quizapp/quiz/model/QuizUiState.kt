package com.example.quizapp.quiz.model

data class QuizUiState(
    val title: String,
    val description: String,
    val quizType: String,
    val questions: List<QuestionUiState>,
    val type: String
)

data class QuestionUiState(
    val question: String,
    val time: Int,
    val points: Boolean,
    val pointsMultiplier: Int,
    val choices: List<ChoiceUiState>,
    val image: String
)

data class ChoiceUiState(
    val answer: String,
    val correct: Boolean
)
