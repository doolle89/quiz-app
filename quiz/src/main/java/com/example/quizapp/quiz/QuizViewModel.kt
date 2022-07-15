package com.example.quizapp.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.quiz.model.QuizScreenUiState
import com.example.quizapp.quiz.model.StringResource
import com.example.quizapp.quiz.model.toUiState
import com.example.quizapp.repository.KahootRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class QuizViewModel : ViewModel() {

    private val kahootId = "fb4054fc-6a71-463e-88cd-243876715bc1"
    private val kahootRepository = KahootRepository.getInstance()

    private val _uiStateFlow = MutableStateFlow(QuizScreenUiState())
    val uiStateFlow: StateFlow<QuizScreenUiState> = _uiStateFlow

    private var timer: Timer? = null

    init {
        viewModelScope.launch {
            setLoadingState(true)
            try {
                val quizUiState = kahootRepository.getKahoot(kahootId).toUiState()
                _uiStateFlow.value = _uiStateFlow.value.copy(quizUiState = quizUiState)
                restartTime()
            } catch (e: Exception) {
                setErrorMessage(StringResource(R.string.error_network))
            } finally {
                setLoadingState(false)
            }
        }
    }

    override fun onCleared() {
        timer?.cancel()
        timer = null
    }

    fun nextQuestion() {
        _uiStateFlow.value.apply {
            _uiStateFlow.value = copy(currentQuestionIndex = currentQuestionIndex + 1)
        }
        restartTime()
    }

    fun selectChoice(position: Int) {
        _uiStateFlow.value.apply {
            if (currentQuestionIndex != selectedAnswers.size) return
            _uiStateFlow.value = copy(selectedAnswers = selectedAnswers + position)
        }
    }

    private fun restartTime() {
        timer?.cancel()
        _uiStateFlow.value.apply {
            _uiStateFlow.value = copy(timeLeft = quizUiState?.questions?.get(currentQuestionIndex)?.time ?: -1)
        }
        timer = Timer().apply {
            scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    updateTimeMinusSecond()
                }
            }, 10, 10)
        }
    }

    fun updateTimeMinusSecond() {
        _uiStateFlow.value.apply {
            if (timeLeft > 0) {
                _uiStateFlow.value = copy(timeLeft = timeLeft - 10)
            } else {
                timer?.cancel()
            }
        }
    }

    fun setErrorMessageShown(errorMessage: StringResource) {
        _uiStateFlow.value.apply {
            _uiStateFlow.value = copy(errorMessages = errorMessages.filterNot { it == errorMessage })
        }
    }

    private fun setErrorMessage(errorMessage: StringResource) {
        _uiStateFlow.value.apply {
            _uiStateFlow.value = copy(errorMessages = errorMessages + errorMessage)
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _uiStateFlow.value = _uiStateFlow.value.copy(isLoading = isLoading)
    }
}