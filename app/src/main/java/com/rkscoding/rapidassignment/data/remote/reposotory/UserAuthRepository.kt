package com.rkscoding.rapidassignment.data.remote.reposotory


import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.common.toParseError
import com.rkscoding.rapidassignment.data.remote.api.AuthApiService
import com.rkscoding.rapidassignment.data.remote.dto.request.ForgotPasswordRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.OtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.ResetPasswordRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.UserRegisterRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.SessionResponse
import retrofit2.HttpException
import javax.inject.Inject

class UserAuthRepository @Inject constructor(private val apiService: AuthApiService) {

    suspend fun sendOtpForRegistration(request: OtpRequest): ApiResponse<Unit> {
        return try {
            apiService.sendOtpForRegistration(request)
        } catch (e: HttpException) { // server side json error response
            throw Exception(e.toParseError())
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = try {
//                Gson().fromJson(errorBody, ApiResponse::class.java)
//            }catch (_: Exception){
//                null
//            }
//            throw Exception(errorResponse?.message ?: "Unknown server error")
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }

    }

    suspend fun register(request: UserRegisterRequest, otp: String): ApiResponse<SessionResponse> {
        return try {
            apiService.register(request, otp)
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun sendOtpForLogin(request: LoginRequest): ApiResponse<Unit> {
        return try {
            apiService.sendOtpForLogin(request)
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun login(request: LoginRequest, otp: String): ApiResponse<SessionResponse> {
        return try {
            apiService.login(request, otp)
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest): ApiResponse<Unit> {
        return try {
            apiService.sendOtpForForgotPassword(request)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun resetPassword(request: ResetPasswordRequest, otp: String) : ApiResponse<Unit> {
        return try {
            apiService.resetPassword(request, otp)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }
}