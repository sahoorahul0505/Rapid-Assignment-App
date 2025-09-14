package com.rkscoding.rapidassignment.data.remote.dto.request

data class QuizSubmissionRequest(
    val quizAccessCode : String,
    val answers : List<AnswerRequest>
)
