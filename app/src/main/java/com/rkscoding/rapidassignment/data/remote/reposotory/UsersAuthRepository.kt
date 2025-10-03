package com.rkscoding.rapidassignment.data.remote.reposotory


import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.common.toParseError
import com.rkscoding.rapidassignment.data.remote.api.AuthApiService
import com.rkscoding.rapidassignment.data.remote.dto.request.PasswordResetOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.LoginRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.RegisterOtpRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.PasswordResetRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.UserRegisterRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.SessionResponse
import retrofit2.HttpException
import javax.inject.Inject

class UsersAuthRepository @Inject constructor(private val apiService: AuthApiService) {

    suspend fun sendOtpForRegistration(request: RegisterOtpRequest): ApiResponse<Unit> {
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

    suspend fun register(request: UserRegisterRequest): ApiResponse<SessionResponse> {
        return try {
            apiService.register(request)
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun sendOtpForLogin(request: LoginOtpRequest): ApiResponse<Unit> {
        return try {
            apiService.sendOtpForLogin(request)
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun login(request: LoginRequest): ApiResponse<SessionResponse> {
        return try {
            apiService.login(request)
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun sendOtpForResetPassword(request: PasswordResetOtpRequest): ApiResponse<Unit> {
        return try {
            apiService.sendOtpForForgotPassword(request)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun resetPassword(request: PasswordResetRequest) : ApiResponse<Unit> {
        return try {
            apiService.resetPassword(request)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }
}