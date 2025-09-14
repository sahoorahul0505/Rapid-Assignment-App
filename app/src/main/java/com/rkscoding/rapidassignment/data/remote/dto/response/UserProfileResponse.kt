package com.rkscoding.rapidassignment.data.remote.dto.response

data class UserProfileResponse(
    val email : String,
    val name : String,
    val rollNumber : String,
    val branch : String,
    val profilePic : String? = null
)
