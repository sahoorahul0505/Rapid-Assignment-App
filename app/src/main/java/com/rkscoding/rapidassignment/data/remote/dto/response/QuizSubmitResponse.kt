package com.rkscoding.rapidassignment.data.remote.dto.response

data class QuizSubmitResponse(
    val quizAccessCode : String,
    val score : Int,
    val totalMarks : Int,
    val percentage : Double,
    val correctAnswersCount : Int,
    val wrongAnswersCount : Int,
    val skippedQuestionsCount : Int
)
