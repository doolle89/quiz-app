package com.example.quizapp.repository.model

import com.example.quizapp.repository.api.model.Choice as ChoiceApiModel
import com.example.quizapp.repository.api.model.Kahoot as KahootApiModel
import com.example.quizapp.repository.api.model.Question as QuestionApiModel

internal fun ChoiceApiModel.toChoice(): Choice {
    return Choice(
        answer = answer ?: throw IllegalArgumentException("answer can't be null"), // handle and logg error in real app
        correct = correct ?: throw IllegalArgumentException("correct can't be null"),
    )
}

internal fun QuestionApiModel.toQuestion(): Question {
    return Question(
        type = type ?: throw IllegalArgumentException("type can't be null"), // handle and logg error in real app
        question = question ?: throw IllegalArgumentException("question can't be null"),
        time = time ?: throw IllegalArgumentException("time can't be null"),
        points = points ?: false,
        pointsMultiplier = pointsMultiplier ?: throw IllegalArgumentException("pointsMultiplier can't be null"),
        choices = choices?.map { it.toChoice() } ?: throw IllegalArgumentException("choices can't be null"),
        image = image ?: throw IllegalArgumentException("image can't be null"),
        resources = resources ?: throw IllegalArgumentException("resources can't be null"),
        questionFormat = questionFormat
    )
}

internal fun KahootApiModel.toKahoot(): Kahoot {
    return Kahoot(
        uuid = uuid ?: throw IllegalArgumentException("uuid can't be null"), // handle and logg error in real app
        title = title ?: throw IllegalArgumentException("title can't be null"),
        description = description ?: throw IllegalArgumentException("description can't be null"),
        quizType = quizType ?: throw IllegalArgumentException("quizType can't be null"),
        cover = cover ?: throw IllegalArgumentException("cover can't be null"),
        questions = questions?.map { it.toQuestion() } ?: throw IllegalArgumentException("questions can't be null"),
        type = type ?: throw IllegalArgumentException("type can't be null")
    )
}