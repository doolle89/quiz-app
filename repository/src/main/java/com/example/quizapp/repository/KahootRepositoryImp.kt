package com.example.quizapp.repository

import com.example.quizapp.repository.api.KahootRemoteDataSource
import com.example.quizapp.repository.model.Kahoot
import com.example.quizapp.repository.model.toKahoot

internal class KahootRepositoryImp(private val remoteDataSource: KahootRemoteDataSource)
    : KahootRepository {

    override suspend fun getKahoot(id: String): Kahoot {
        val result = remoteDataSource.getKahoot(id)
        return result.toKahoot()
    }
}