package com.rkscoding.rapidassignment.data.remote.dto.response

data class UserQuizQuestionResponse(
    val questionId : String?,
    val questionText : String,
    val options : List<String>
)
