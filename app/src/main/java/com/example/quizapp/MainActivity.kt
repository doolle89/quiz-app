package com.example.quizapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.quiz.QuizFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuizFragment())
                .commitNow()
        }
    }
}