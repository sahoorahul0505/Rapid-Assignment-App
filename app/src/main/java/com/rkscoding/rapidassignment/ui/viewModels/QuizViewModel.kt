package com.rkscoding.rapidassignment.ui.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.rkscoding.rapidassignment.data.common.BaseViewModel
import com.rkscoding.rapidassignment.data.common.UiEvent
import com.rkscoding.rapidassignment.data.common.UiState
import com.rkscoding.rapidassignment.data.remote.dto.request.AnswerRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UserQuizQuestionResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import com.rkscoding.rapidassignment.data.remote.reposotory.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val quizRepository: QuizRepository) : BaseViewModel() {

    private val _quizState = MutableStateFlow<UiState<UsersQuizDetailsResponse>>(UiState.Idle)
    val quizState = _quizState.asStateFlow()

    private val _quizQuestionState = MutableStateFlow<UiState<List<UserQuizQuestionResponse>>>(UiState.Idle)
    val quizQuestionState = _quizQuestionState.asStateFlow()

    private val _quizCodeFormCode = MutableStateFlow(QuizCodeFormState())
    val quizCodeFormState = _quizCodeFormCode.asStateFlow()

    private val _selectedAnswers = MutableStateFlow<Map<String, Int>>(emptyMap())
    val selectedAnswers = _selectedAnswers.asStateFlow()

    private val _quizSubmissionState = MutableStateFlow<UiState<QuizSubmitResponse>>(UiState.Idle)
    val quizSubmissionState = _quizSubmissionState.asStateFlow()

    private val _allSubmissionsState = MutableStateFlow<UiState<List<QuizSubmitResponse>>>(UiState.Idle)
    val allSubmissionsState = _allSubmissionsState.asStateFlow()

    private val _quizNavEvent = MutableSharedFlow<QuizNavEvent>(replay = 1)
    val quizNavEvent = _quizNavEvent.asSharedFlow()
    fun onFormChange(copy: QuizCodeFormState) {
        viewModelScope.launch {
            _quizCodeFormCode.value = copy
        }
    }

    fun onRecentQuizClicked() {
        viewModelScope.launch {
            _quizNavEvent.emit(
                QuizNavEvent.NavigateToResult(
                    quizResult = QuizSubmitResponse(
                        quizCode = "JAVDT8399",
                        score = 28,
                        totalMarks = 30,
                        percentage = 93.33,
                        correctAnswersCount = 28,
                        wrongAnswersCount = 2,
                        skippedQuestionsCount = 0,
                        submittedAt = "01-10-2025, 14:10"
                    )
                )
            )
        }
    }

    fun getQuizDetails() {
        val currentForm = _quizCodeFormCode.value
        viewModelScope.launch {
            _quizState.value = UiState.Loading

            try {
                val quizDetails = quizRepository.getQuizDetailsForUser(currentForm.quizCode)
                if (quizDetails.data != null) {
                    val data = quizDetails.data
                    _quizState.value = UiState.Success(data)
                    _quizNavEvent.emit(QuizNavEvent.NavigateToQuiz(quizDetails = data))
                }
            } catch (e: Exception) {
                _quizState.value = UiState.Error(e.message ?: "Unknown error.")
                sendUiEvent(
                    event = UiEvent.ShowSnackBar(message = e.localizedMessage ?: "Unknown error."),
                    scoped = this
                )
            }
        }
    }

    fun getUserQuizQuestions() {
        val currentForm = _quizCodeFormCode.value
        viewModelScope.launch {
            _quizQuestionState.value = UiState.Loading

            try {
                val result = quizRepository.getQuizQuestionsForUser(quizCode = currentForm.quizCode)
                _quizQuestionState.value = UiState.Success(data = result.data!!)
                Log.d("Questions", "${result.data}")
            } catch (e: Exception) {
                _quizQuestionState.value = UiState.Error(e.localizedMessage ?: "Unknown error.")
                sendUiEvent(UiEvent.ShowSnackBar(e.message ?: "Unknown error."), scoped = this)
            }
        }
    }


    fun selectOption(questionId: String, optionIndex: Int) {
        viewModelScope.launch {
            _selectedAnswers.value = _selectedAnswers.value.toMutableMap().apply {
                put(questionId, optionIndex)
            }
        }
    }

    fun submitQuiz() {
        val currForm = _quizCodeFormCode.value
        val answers = _selectedAnswers.value.map { (qId, index) ->
            AnswerRequest(questionId = qId, selectedOptionIndex = index)
        }

        viewModelScope.launch {
            _quizSubmissionState.value = UiState.Loading
            try {
                val result = quizRepository.submitQuizForUser(
                    quizCode = currForm.quizCode,
                    request = answers
                )
                if (result.data != null) {
                    val data = result.data
                    _quizSubmissionState.value = UiState.Success(data = data)
                    _quizNavEvent.emit(
                        QuizNavEvent.NavigateToResult(quizResult = data)
                    )
                } else {
                    _quizSubmissionState.value = UiState.Error("No response data")
                }
            } catch (e: Exception) {
                _quizSubmissionState.value = UiState.Error(e.message ?: "Unknown error.")
                sendUiEvent(UiEvent.ShowSnackBar(e.localizedMessage ?: "Failed to Submit"), scoped = this)
            }
        }
    }

    fun getAllSubmissions() {
        viewModelScope.launch {
            _allSubmissionsState.value = UiState.Loading
            try {
                val result = quizRepository.getAllSubmissions()
                _allSubmissionsState.value = UiState.Success(data = result.data ?: emptyList())
            }catch (e : Exception){
                _allSubmissionsState.value = UiState.Error(e.message ?: "Failed to fetch submissions")
            }
        }
    }


    data class QuizCodeFormState(
        val quizCode: String = "",
    )

    sealed class QuizNavEvent {
        data class NavigateToQuiz(
            val quizDetails: UsersQuizDetailsResponse
        ) : QuizNavEvent()
        data class NavigateToResult(val quizResult: QuizSubmitResponse) : QuizNavEvent()
        object NavigateToHome : QuizNavEvent()
    }
}