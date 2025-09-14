package com.rkscoding.rapidassignment.data.common

import com.google.gson.Gson
import retrofit2.HttpException

fun HttpException.toParseError() : String {
    return try {
        val errorBody = response()?.errorBody()?.string()
        val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)
        errorResponse?.message ?: "Unknown server error"
    }catch (e : Exception){
        "Something went wrong"
    }
}