package com.example.quizapp.quiz.model

import com.example.quizapp.repository.model.Choice
import com.example.quizapp.repository.model.Kahoot
import com.example.quizapp.repository.model.Question


internal fun Choice.toUiState(): ChoiceUiState {
    return ChoiceUiState(
        answer = answer,
        correct = correct
    )
}

internal fun Question.toUiState(): QuestionUiState {
    return QuestionUiState(
        question = question,
        time = time,
        points = points,
        pointsMultiplier = pointsMultiplier,
        choices = choices.map { it.toUiState() },
        image = image
    )
}

internal fun Kahoot.toUiState(): QuizUiState {
    if (type != "quiz") throw IllegalArgumentException("This Kahoot is not quiz type") // log error
    return QuizUiState(
        title = title,
        description = description,
        quizType = quizType,
        questions = questions.map { it.toUiState() },
        type = type
    )

}