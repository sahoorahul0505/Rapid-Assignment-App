package com.rkscoding.rapidassignment.data.remote.dto.request

data class UserRegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val rollNumber: String,
    val branch: String,
    val otp : String
)
