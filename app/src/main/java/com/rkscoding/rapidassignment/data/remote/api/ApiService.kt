package com.rkscoding.rapidassignment.data.remote.api

import com.rkscoding.rapidassignment.data.model.request.RegisterRequest
import com.rkscoding.rapidassignment.data.remote.dto.GenericResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("/auth/register")
    suspend fun register(@Body request: RegisterRequest) : Response<GenericResponseDto>
}