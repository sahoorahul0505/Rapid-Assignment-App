package com.rkscoding.rapidassignment.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuizDetails(
    val subject : String,
    val topic : String,
    val quizCode : String,
    val teacherName : String,
    val totalMarks : Int
) : Parcelable
