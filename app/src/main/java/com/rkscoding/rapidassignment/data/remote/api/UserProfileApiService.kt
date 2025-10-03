package com.rkscoding.rapidassignment.data.remote.api

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.remote.dto.request.UserProfileUpdateRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface UserProfileApiService {

    @GET("/users/profile")
    suspend fun fetchUserProfile(): ApiResponse<UserProfileResponse>

    @PUT("/users/profile/update")
    suspend fun updateUserProfile(
        @Body request: UserProfileUpdateRequest,
        @Query("password") password: String
    ): ApiResponse<Unit>

    @Multipart
    @PATCH("/users/profile/picture")
    suspend fun uploadUserProfilePic(
        @Part file : MultipartBody.Part
    ) : ApiResponse<Unit>
}