package com.rkscoding.rapidassignment.data.remote.api

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.remote.dto.request.ForgotPasswordRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.OtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.ResetPasswordRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.UserRegisterRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.SessionResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApiService {

    // -------------------------- REGISTRATION --------------------------------------//

    @POST("/auth/user/send-registration-otp")
    suspend fun sendOtpForRegistration(@Body request: OtpRequest): ApiResponse<Unit>

    @POST("/auth/user/register")
    suspend fun register(
        @Body request: UserRegisterRequest,
        @Query("otp") otp: String
    ): ApiResponse<SessionResponse>

    // ------------------------------ LOGIN -----------------------------------------//

    @POST("/auth/user/send-login-otp")
    suspend fun sendOtpForLogin(@Body request: LoginRequest): ApiResponse<Unit>

    @POST("/auth/user/login")
    suspend fun login(
        @Body request: LoginRequest,
        @Query("otp") otp: String
    ): ApiResponse<SessionResponse>

    @POST("/auth/user/forgot-password-otp")
    suspend fun sendOtpForForgotPassword(@Body request: ForgotPasswordRequest): ApiResponse<Unit>

    @POST("/auth/user/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest, @Query("otp") otp: String): ApiResponse<Unit>
}