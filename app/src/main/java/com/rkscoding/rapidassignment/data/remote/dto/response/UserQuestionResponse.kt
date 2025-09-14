package com.rkscoding.rapidassignment.data.remote.dto.response

data class UserQuestionResponse(
    val id : String?,
    val questionText : String,
    val options : List<String>
)
