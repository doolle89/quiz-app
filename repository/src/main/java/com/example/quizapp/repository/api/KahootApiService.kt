package com.example.quizapp.repository.api

import com.example.quizapp.repository.api.model.Kahoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class KahootApiService(
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : KahootRemoteDataSource {

    private val kahootApi = KahootApiInterface.create()

    override suspend fun getKahoot(id: String): Kahoot = withContext(coroutineContext) {
        kahootApi.getKahoot(id)
    }
}