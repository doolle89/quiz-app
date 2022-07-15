package com.example.quizapp.repository

import com.example.quizapp.repository.api.KahootApiService
import com.example.quizapp.repository.model.Kahoot

interface KahootRepository {
    suspend fun getKahoot(id: String): Kahoot

    companion object {
        fun getInstance(): KahootRepository {
            return KahootRepositoryImp(KahootApiService())
        }
    }
}