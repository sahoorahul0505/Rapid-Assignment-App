package com.rkscoding.rapidassignment.data.remote.reposotory


import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.common.toParseError
import com.rkscoding.rapidassignment.data.remote.api.UserProfileApiService
import com.rkscoding.rapidassignment.data.remote.dto.request.UserProfileUpdateRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject

class UserProfileRepository @Inject constructor(private val apiService: UserProfileApiService) {
    suspend fun fetchUserProfile(): ApiResponse<UserProfileResponse> {
        return try {
            apiService.fetchUserProfile()
        } catch (ex: HttpException) {
            throw Exception(ex.toParseError())
        } catch (e: Exception) {
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun updateUserProfile(request: UserProfileUpdateRequest, password: String): ApiResponse<Unit> {
        return try {
            apiService.updateUserProfile(request, password)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }

    suspend fun uploadUserProfilePic(file : MultipartBody.Part) : ApiResponse<Unit>{
        return try {
            apiService.uploadUserProfilePic(file)
        }catch (ex : HttpException){
            throw Exception(ex.toParseError())
        }catch (e : Exception){
            throw Exception(e.message ?: "Something went wrong")
        }
    }
}