package com.example.quizapp.repository.api

import com.example.quizapp.repository.api.model.Kahoot

internal interface KahootRemoteDataSource {
    suspend fun getKahoot(id: String): Kahoot
}