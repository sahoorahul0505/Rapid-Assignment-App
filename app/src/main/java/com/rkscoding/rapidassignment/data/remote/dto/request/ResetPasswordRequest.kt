package com.rkscoding.rapidassignment.data.remote.dto.request

data class ResetPasswordRequest(
    val email : String,
    val newPassword : String
)
