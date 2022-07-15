package com.example.quizapp.quiz

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.quizapp.quiz.databinding.FragmentQuizBinding
import com.example.quizapp.quiz.databinding.ItemQuizButtonBinding
import com.example.quizapp.quiz.model.ChoiceUiState
import com.example.quizapp.quiz.model.QuestionUiState
import com.example.quizapp.quiz.model.QuizScreenUiState
import com.example.quizapp.quiz.model.StringResource
import kotlinx.coroutines.launch

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    setupUI(uiState)
                    handleErrorMessages(uiState.errorMessages)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI(quizScreenUiState: QuizScreenUiState) {
        if (quizScreenUiState.quizUiState != null) {
            binding.quizLoadingProgressBar.visibility = View.GONE
            binding.quizContainerGroup.visibility = View.VISIBLE
            val questionIndex = quizScreenUiState.currentQuestionIndex
            val selectedChoice = quizScreenUiState.selectedAnswers.getOrElse(questionIndex) { -1 }
            binding.progressCounterTextView.text = "${questionIndex+1}/${quizScreenUiState.quizUiState.questions.size}"
            binding.continueButton.visibility = if (
                quizScreenUiState.quizUiState.questions.size - 1 > quizScreenUiState.currentQuestionIndex &&
                selectedChoice > -1
                    ) View.VISIBLE else View.INVISIBLE
            binding.continueButton.setOnClickListener {
                viewModel.nextQuestion()
            }

            val currentQuestion = quizScreenUiState.quizUiState.questions[questionIndex]
            setupQuestionUI(currentQuestion, selectedChoice)
            setupTimer(currentQuestion.time, quizScreenUiState.timeLeft)
        } else {
            binding.quizContainerGroup.visibility = View.GONE
            binding.quizLoadingProgressBar.visibility = View.VISIBLE
        }
    }

    private fun setupQuestionUI(questionUiState: QuestionUiState, selectedChoice: Int) {
        val choiceSelected = selectedChoice > -1
        Glide.with(this)
            .load(questionUiState.image)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
            .into(binding.quizImageView)
        binding.questionTextView.text =  HtmlCompat.fromHtml(questionUiState.question, HtmlCompat.FROM_HTML_MODE_COMPACT)
        binding.questionProgressBar.visibility = if (choiceSelected) View.GONE else View.VISIBLE
        setupChoices(questionUiState.choices, selectedChoice)
        setupAnswerStatus(questionUiState.choices.getOrNull(selectedChoice))
    }

    private fun setupTimer(totalTime: Int, remainingTime: Int) {
        binding.questionProgressBar.max = totalTime
        binding.questionProgressBar.progress = remainingTime
    }

    private fun setupChoices(choices: List<ChoiceUiState>, selectedChoice: Int) {
        setupChoice(binding.choice0, 0, choices, selectedChoice)
        setupChoice(binding.choice1, 1, choices, selectedChoice)
        setupChoice(binding.choice2, 2, choices, selectedChoice)
        setupChoice(binding.choice3, 3, choices, selectedChoice)
    }

    private fun setupChoice(
        itemQuizButtonBinding: ItemQuizButtonBinding,
        position: Int,
        choices: List<ChoiceUiState>,
        selectedChoice: Int
    ) {
        val isVisible = choices.size > position
        itemQuizButtonBinding.root.visibility = if (isVisible) View.VISIBLE else View.GONE
        if (!isVisible) return
        itemQuizButtonBinding.root.setOnClickListener {
            viewModel.selectChoice(position)
        }
        itemQuizButtonBinding.answerTextView.text = choices[position].answer

        setupChoiceTextView(itemQuizButtonBinding, position, choices, selectedChoice)
        setupChoiceIcon(itemQuizButtonBinding, position, choices, selectedChoice)
    }

    private fun setupChoiceTextView(
        itemQuizButtonBinding: ItemQuizButtonBinding,
        position: Int,
        choices: List<ChoiceUiState>,
        selectedChoice: Int
    ) {
        val layoutParams = itemQuizButtonBinding.answerTextView.layoutParams as FrameLayout.LayoutParams
        if (position.mod(2) == 0) {
            val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
            val top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
            val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics).toInt()
            val bottom = 0
            layoutParams.setMargins(left, top, right, bottom)
        } else {
            val left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4f, resources.displayMetrics).toInt()
            val top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
            val right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
            val bottom = 0
            layoutParams.setMargins(left, top, right, bottom)
        }
        val color = if (selectedChoice > -1) {
            if (choices[position].correct) {
                Color.parseColor("#66BF39")
            } else if (selectedChoice == position && !choices[selectedChoice].correct) {
                Color.parseColor("#FF3355")
            }
            else {
                Color.parseColor("#FF99AA")
            }
        } else when(position) {
            0 -> Color.parseColor("#E21B3C")
            1 -> Color.parseColor("#1368CE")
            2 -> Color.parseColor("#D89E00")
            3 -> Color.parseColor("#26890C")
            else -> throw IllegalArgumentException("Position not supported")
        }
        itemQuizButtonBinding.answerTextView.background.setTint(color)
    }

    private fun setupChoiceIcon(
        itemQuizButtonBinding: ItemQuizButtonBinding,
        position: Int,
        choices: List<ChoiceUiState>,
        selectedChoice: Int
    ) {
        val layoutParams = itemQuizButtonBinding.iconImageView.layoutParams as FrameLayout.LayoutParams
        if (selectedChoice > -1) {
            layoutParams.setMargins(0)
            layoutParams.gravity = Gravity.TOP or if (position.mod(2) == 0) Gravity.START else Gravity.END
            val icon = if (choices[position].correct) R.drawable.correct else R.drawable.wrong
            itemQuizButtonBinding.iconImageView.setImageResource(icon)
        } else {
            val margins = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, resources.displayMetrics).toInt()
            layoutParams.setMargins(margins)
            layoutParams.gravity = Gravity.TOP or Gravity.START
            val icon = when(position) {
                0 -> R.drawable.triangle
                1 -> R.drawable.diamond
                2 -> R.drawable.circle
                3 -> R.drawable.square
                else -> throw IllegalArgumentException("Position not supported")
            }
            itemQuizButtonBinding.iconImageView.setImageResource(icon)
        }
    }

    private fun setupAnswerStatus(choice: ChoiceUiState?) {
        if (choice == null) {
            binding.answerStatusTextView.visibility = View.GONE
            return
        }
        binding.answerStatusTextView.visibility = View.VISIBLE
        binding.answerStatusTextView.text = if (choice.correct) getString(R.string.answer_correct)
        else getString(R.string.answer_wrong)
        binding.answerStatusTextView.setBackgroundColor(if (choice.correct)
            Color.parseColor("#66BF39") else Color.parseColor("#FF3355"))
    }

    private fun handleErrorMessages(errorMessages: List<StringResource?>) {
        val errorMessage = errorMessages.firstOrNull() ?: return
        Toast.makeText(requireContext(), errorMessage.resolve(requireContext()), Toast.LENGTH_SHORT).show()
        viewModel.setErrorMessageShown(errorMessage)
    }
}