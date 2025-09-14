package com.rkscoding.rapidassignment.data.remote.dto.request

data class UserProfileUpdateRequest(
    val name : String? = null,
    val rollNumber : String? = null,
    val branch : String? = null,
    val profilePic : String? = null
)
