package com.rkscoding.rapidassignment.data.common

data class ApiResponse<T>(
    val statusCode: Int,
    val message: String,
    val data: T?
)
