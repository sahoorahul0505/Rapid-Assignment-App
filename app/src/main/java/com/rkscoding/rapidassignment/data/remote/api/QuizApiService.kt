package com.rkscoding.rapidassignment.data.remote.api

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.remote.dto.request.AnswerRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UserQuizQuestionResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UsersQuizDetailsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizApiService {


    @GET("/users/quizzes/{quizCode}")
    suspend fun getQuizForUser(
        @Path("quizCode") quizCode : String
    ) : ApiResponse<UsersQuizDetailsResponse>

    @GET("/users/quizzes/{quizCode}/questions")
    suspend fun getQuizQuestionsForUser(
        @Path("quizCode") quizCode : String
    ): ApiResponse<List<UserQuizQuestionResponse>>

    @POST("/users/quizzes/{quizCode}/submission")
    suspend fun submitQuizForUser(
        @Path("quizCode") quizCode : String,
        @Body request: List<AnswerRequest>
    ): ApiResponse<QuizSubmitResponse>

    @GET("/users/quizzes/submissions")
    suspend fun getAllSubmissionsForUser() : ApiResponse<List<QuizSubmitResponse>>
}