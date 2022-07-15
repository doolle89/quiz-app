package com.example.quizapp.repository.api

import com.example.quizapp.repository.api.model.Kahoot
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

internal interface KahootApiInterface {

    @GET("rest/kahoots/{id}")
    suspend fun getKahoot(@Path("id") id: String): Kahoot

    companion object {
        fun create(): KahootApiInterface {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://create.kahoot.it/")
                .build()
                .create(KahootApiInterface::class.java)
        }
    }
}