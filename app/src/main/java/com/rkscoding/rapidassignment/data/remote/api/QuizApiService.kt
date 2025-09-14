package com.rkscoding.rapidassignment.data.remote.api

import com.rkscoding.rapidassignment.data.common.ApiResponse
import com.rkscoding.rapidassignment.data.remote.dto.request.GetQuestionsRequest
import com.rkscoding.rapidassignment.data.remote.dto.request.QuizSubmissionRequest
import com.rkscoding.rapidassignment.data.remote.dto.response.QuizSubmitResponse
import com.rkscoding.rapidassignment.data.remote.dto.response.UserQuestionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface QuizApiService {

    @POST("/user/quiz/get-all-question")
    suspend fun getAllQuestionsForUser(
        @Body request: GetQuestionsRequest
    ): ApiResponse<List<UserQuestionResponse>>

    @POST("/user/quiz/submit-quiz")
    suspend fun submitQuizForUser(
        @Body request: QuizSubmissionRequest
    ): ApiResponse<QuizSubmitResponse>
}