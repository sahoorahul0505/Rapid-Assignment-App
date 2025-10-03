package com.rkscoding.rapidassignment.data.remote.api

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.remote.dto.request.PasswordResetOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.RegisterOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.PasswordResetRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.UserRegisterRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.SessionResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApiService {

    // -------------------------- REGISTRATION --------------------------------------//

    @POST("/users/auth/register/otp")
    suspend fun sendOtpForRegistration(@Body request: RegisterOtpRequest): ApiResponse<Unit>

    @POST("/users/auth/register")
    suspend fun register(
        @Body request: UserRegisterRequest
    ): ApiResponse<SessionResponse>

    // ------------------------------ LOGIN -----------------------------------------//

    @POST("/users/auth/login/otp")
    suspend fun sendOtpForLogin(@Body request: LoginOtpRequest): ApiResponse<Unit>

    @POST("/users/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): ApiResponse<SessionResponse>

    @POST("/users/auth/password/reset/otp")
    suspend fun sendOtpForForgotPassword(@Body request: PasswordResetOtpRequest): ApiResponse<Unit>

    @POST("/users/auth/password/reset")
    suspend fun resetPassword(@Body request: PasswordResetRequest): ApiResponse<Unit>
}