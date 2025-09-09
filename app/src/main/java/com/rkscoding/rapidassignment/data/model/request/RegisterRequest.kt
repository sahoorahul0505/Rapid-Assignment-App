package com.rkscoding.rapidassignment.data.model.request

data class RegisterRequest(
    val email : String,
    val password : String,
    val name : String,
    val rollNumber : String,
    val branch : String,
)
