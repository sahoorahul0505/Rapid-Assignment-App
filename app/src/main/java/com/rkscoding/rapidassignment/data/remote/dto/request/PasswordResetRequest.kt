package com.rkscoding.rapidassignment.data.remote.dto.request

data class PasswordResetRequest(
    val email : String,
    val newPassword : String,
    val otp : String
)
