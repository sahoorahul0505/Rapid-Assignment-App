package com.rkscoding.rapidassignment.data.remote.dto.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class QuizSubmitResponse(
    val quizCode : String,
    val score : Int,
    val totalMarks : Int,
    val percentage : Double,
    val correctAnswersCount : Int,
    val wrongAnswersCount : Int,
    val skippedQuestionsCount : Int,
    val submittedAt : String
)
