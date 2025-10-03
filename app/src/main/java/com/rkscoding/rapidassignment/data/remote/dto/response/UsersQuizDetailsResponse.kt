package com.rkscoding.rapidassignment.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class UsersQuizDetailsResponse(
    val subject : String,
    val topic : String,
    val quizCode : String,
    val teacherName : String,
    val totalMarks : Int
)
